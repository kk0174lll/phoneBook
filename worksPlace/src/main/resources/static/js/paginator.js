'use strict';

var PAGINATOR = {};

PAGINATOR.paging = function ($block_result, callbackOnPageChange, arrItemCount) {
  if (!arrItemCount) {
    arrItemCount = [5, 10, 25, 50];
  }

  var setPage = function (page, count) {
    callbackOnPageChange(page, count);
  };

  $block_result.on("draw", function () {

    var $table = $block_result.find("table:first");
    if ($table.length !== 1) {
      return;
    }

    var htmlItemCountOptions = '';
    for (var i = 0; i < arrItemCount.length; i++) {
      htmlItemCountOptions += '<option value="' + arrItemCount[i] + '">' + arrItemCount[i] + "</option>";
    }

    var htmlPaging = '<div class="paging"><div class="right"><label>Выводить по ' + '<select name="show_item_count">' + htmlItemCountOptions + '</select></label></div>' + '<a href="javascript:void(0);" class="first">Первая</a>' + '<a href="javascript:void(0);" class="prev"><-</a>' + '<div class="div-pages"></div>' + '<a href="javascript:void(0);" class="next">-></a>' + '<a href="javascript:void(0);" class="last">Последняя</a>&nbsp;</div>';

    //удаляем предыдущий сгенерированный пейджинг, если есть
    $block_result.find(".paging").remove();

    $table.before(htmlPaging);
    var $paging = $table.prev();

    var $selectItemCount = $paging.find("select[name='show_item_count']");
    $selectItemCount.val($block_result.data("item-count") ? $block_result.data("item-count") : arrItemCount[0]);

    var cntPage = Math.ceil(+$table.data("paging-count") / $selectItemCount.val());
    var currentPage = $table.data("paging-page") ? +$table.data("paging-page") : 1;
    if (currentPage > cntPage) {
      currentPage = cntPage;
    }
    var htmlPages = '';

    var RADIUS = 2;
    var showBegin = currentPage, showEnd = currentPage;
    var currentLength = 1, length = RADIUS * 2 + 1;
    while (currentPage - showBegin < RADIUS && showBegin > 1) {
      showBegin--;
      currentLength++;
    }
    while (showEnd - currentPage < RADIUS && showEnd < cntPage) {
      showEnd++;
      currentLength++;
    }
    while (currentLength < length && showBegin > 1) {
      showBegin--;
      currentLength++;
    }
    while (currentLength < length && showEnd < cntPage) {
      showEnd++;
      currentLength++;
    }

    if (showBegin > 1) {
      htmlPages += '<a href="javascript:void(0);" class="prevOther">...</a>';
    }
    for (i = showBegin; i <= showEnd; i++) {
      htmlPages += '<a href="javascript:void(0);" data-page="' + i + '" class="' + (i == currentPage ? 'current' : '') + '">' + i + '</a>';
    }
    if (showEnd < cntPage) {
      htmlPages += '<a href="javascript:void(0);" class="nextOther">...</a>';
    }

    $paging.find(".div-pages").html(htmlPages);

    $paging.find("a").click(function () {
      var page;
      var $a = $paging.find("a");
      if ($(this).hasClass("first")) {
        page = 1;
      } else if ($(this).hasClass("prev")) {
        page = +$a.filter(".current").data("page") - 1;
      } else if ($(this).hasClass("next")) {
        page = +$a.filter(".current").data("page") + 1;
      } else if ($(this).hasClass("last")) {
        page = cntPage;
      } else if ($(this).hasClass("prevOther")) {
        page = +$a.filter(".current").data("page") - 1 - RADIUS * 2;
      } else if ($(this).hasClass("nextOther")) {
        page = +$a.filter(".current").data("page") + 1 + RADIUS * 2;
      } else {
        page = $(this).data("page");
      }
      if (page < 1) {
        page = 1
      } else if (page > cntPage) {
        page = cntPage;
      }
      setPage(page, $selectItemCount.val());
    });

    $selectItemCount.change(function () {
      $block_result.data("item-count", $(this).val());
      setPage(1, $(this).val());
    });

    if (!cntPage || cntPage <= 1) {
      $paging.find("a, .div-pages").hide();
    }

    $paging.show();

  })
};