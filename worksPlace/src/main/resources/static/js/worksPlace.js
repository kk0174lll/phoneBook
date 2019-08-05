'use strict';
$(document).ready(function () {
  worksPlace.init();
});

var worksPlace = {

  inputSurname: null,
  inputName: null,
  inputWork: null,
  inputAddress: null,

  searchResults: null,
  resultsTable: null,
  resultsTableBody: null,

  btnSearch: null,
  btnAdd: null,
  btnClear: null,

  DEF_COUNT: 5,

  init: function () {
    worksPlace.inputSurname = $("#inputSurname");
    worksPlace.inputName = $("#inputName");
    worksPlace.inputWork = $("#inputWork");
    worksPlace.inputAddress = $("#inputAddress");
    worksPlace.btnSearch = $("#btnSearch");
    worksPlace.btnSearch.click(function () {
      worksPlace.search(null, null)
    });
    worksPlace.btnAdd = $("#btnAdd");
    worksPlace.btnAdd.click(worksPlace.addData);
    worksPlace.btnClear = $("#btnClear");
    worksPlace.btnClear.click(worksPlace.clearFilter);
    worksPlace.searchResults = $("#search_results");
    PAGINATOR.paging(worksPlace.searchResults, worksPlace.search);
    worksPlace.resultsTable = $("#resultsTable");
    worksPlace.resultsTableBody = $("#resultsTableBody");
    worksPlace.search(null, null);
  },

  getData: function () {
    return {
      surname: worksPlace.inputSurname.val(),
      name: worksPlace.inputName.val(),
      work: worksPlace.inputWork.val(),
      address: worksPlace.inputAddress.val()
    }
  },

  clearFilter: function () {
    worksPlace.inputSurname.val("");
    worksPlace.inputName.val("");
    worksPlace.inputWork.val("");
    worksPlace.inputAddress.val("");
  },

  checkData: function (data) {
    if (!$.trim(data.surname)) {
      UTILS.error("Не заполнено поле \"Фамилия\"");
      return false;
    }
    if (!$.trim(data.name)) {
      UTILS.error("Не заполнено поле \"Имя\"");
      return false;
    }
    if (!$.trim(data.work)) {
      UTILS.error("Не заполнено поле \"Место работы\"");
      return false;
    }
    if (!$.trim(data.address)) {
      UTILS.error("Не заполнено поле \"Адрес работы\"");
      return false;
    }

    return true;
  },

  search: function (page, count) {
    var data = {};
    data.filter = worksPlace.getData();
    data.page = page || 1;
    data.count = count || worksPlace.searchResults.data("item-count") || worksPlace.DEF_COUNT;
    UTILS.showWaitBlock();
    $.when(UTILS.sendRequest("/worksPlace/search", JSON.stringify(data), worksPlace.renderTable))
      .always(UTILS.hideWaitBlock);
  },

  addData: function () {
    var data = worksPlace.getData();
    if (!worksPlace.checkData(data)) {
      return;
    }
    var success = function (json) {
      if (UTILS.isError(UTILS.getJson(json))) {
        return;
      }
      UTILS.success("Новая запись успешно добавлена");
      worksPlace.clearFilter();
      worksPlace.search(null, null);
    };
    UTILS.showWaitBlock();
    $.when(UTILS.sendRequest("/worksPlace/add", JSON.stringify(data), success))
      .always(UTILS.hideWaitBlock);
  },

  setPaging: function (params) {
    $("#allCount").html(params.count);
    worksPlace.resultsTable.data({
                                   "paging-count": params ? params.count : null,
                                   "paging-page": params ? params.page : null
                                 });
    worksPlace.searchResults.trigger("draw");
  },

  renderTable: function (json) {
    var data = UTILS.getJson(json);
    if (UTILS.isError(data)) {
      return;
    }
    worksPlace.fillTableBody(data);
    worksPlace.setPaging(data);
  },

  fillTableBody: function (data) {
    worksPlace.resultsTableBody.empty();
    if (!data || UTILS.isEmpty(data.searchResult)) {
      var $td = $('<td>').attr('colspan', 99).html("Данные не найдены");
      var $tr = $('<tr class="empty_res">').append($td);
      worksPlace.resultsTableBody.append($tr);
      return;
    }
    data.searchResult.forEach(function (e) {
      worksPlace.resultsTableBody.append(worksPlace.createRow(e));
    });
  },

  createRow: function (data) {
    var $tr = $('<tr>');
    $tr.append(UTILS.createTD(data.surname));
    $tr.append(UTILS.createTD(data.name));
    $tr.append(UTILS.createTD(data.work));
    $tr.append(UTILS.createTD(data.address));
    return $tr;
  }

};
