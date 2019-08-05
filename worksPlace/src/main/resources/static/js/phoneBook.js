'use strict';
$(document).ready(function () {
  phoneBook.init();
});

var phoneBook = {

  lastName: null,
  firstName: null,
  workPhone: null,
  mobilePhone: null,
  mail: null,
  birthDate: null,
  work: null,

  searchResults: null,
  resultsTable: null,
  resultsTableBody: null,

  btnSearch: null,
  btnAdd: null,
  btnClear: null,

  DEF_COUNT: 5,

  init: function () {
    phoneBook.lastName = $("#lastName");
    phoneBook.firstName = $("#firstName");
    phoneBook.workPhone = $("#workPhone");
    phoneBook.mobilePhone = $("#mobilePhone");
    phoneBook.mail = $("#mail");
    phoneBook.birthDate = $("#birthDate");
    phoneBook.work = $("#work");
    phoneBook.btnSearch = $("#btnSearch");
    phoneBook.btnSearch.click(function () {
      phoneBook.search(null, null)
    });
    phoneBook.btnClear = $("#btnClear");
    phoneBook.btnClear.click(phoneBook.clearFilter);
    phoneBook.searchResults = $("#search_results");
    PAGINATOR.paging(phoneBook.searchResults, phoneBook.search);
    phoneBook.resultsTable = $("#resultsTable");
    phoneBook.resultsTableBody = $("#resultsTableBody");
    phoneBook.search(null, null);
  },

  getData: function () {
    return {
      lastName: phoneBook.lastName.val(),
      firstName: phoneBook.firstName.val(),
      workPhone: phoneBook.workPhone.val(),
      mobilePhone: phoneBook.mobilePhone.val(),
      mail: phoneBook.mail.val(),
      birthDate: phoneBook.birthDate.val(),
      work: phoneBook.work.val()
    }
  },

  clearFilter: function () {
    phoneBook.lastName.val("");
    phoneBook.firstName.val("");
    phoneBook.workPhone.val("");
    phoneBook.mobilePhone.val("");
    phoneBook.mail.val("");
    phoneBook.birthDate.val("");
    phoneBook.work.val("");
  },

  search: function (page, count) {
    var data = {};
    data.filter = phoneBook.getData();
    data.page = page || 1;
    data.count = count || phoneBook.searchResults.data("item-count") || phoneBook.DEF_COUNT;
    UTILS.showWaitBlock();
    $.when(UTILS.sendRequest("/phoneBook/search", JSON.stringify(data), phoneBook.renderTable))
      .always(UTILS.hideWaitBlock);
  },

  setPaging: function (params) {
    $("#allCount").html(params.count);
    phoneBook.resultsTable.data({
                                  "paging-count": params ? params.count : null,
                                  "paging-page": params ? params.page : null
                                });
    phoneBook.searchResults.trigger("draw");
  },

  renderTable: function (json) {
    var data = UTILS.getJson(json);
    if (UTILS.isError(data)) {
      return;
    }
    phoneBook.fillTableBody(data);
    phoneBook.setPaging(data);
  },

  fillTableBody: function (data) {
    phoneBook.resultsTableBody.empty();
    if (!data || UTILS.isEmpty(data.searchResult)) {
      var $td = $('<td>').attr('colspan', 99).html("Данные не найдены");
      var $tr = $('<tr class="empty_res">').append($td);
      phoneBook.resultsTableBody.append($tr);
      return;
    }
    data.searchResult.forEach(function (e) {
      phoneBook.resultsTableBody.append(phoneBook.createRow(e));
    });
  },

  createRow: function (data) {
    var $tr = $('<tr>');
    $tr.append(UTILS.createTD(data.lastName));
    $tr.append(UTILS.createTD(data.firstName));
    $tr.append(UTILS.createTD(data.workPhone));
    $tr.append(UTILS.createTD(data.mobilePhone));
    $tr.append(UTILS.createTD(data.mail));
    $tr.append(UTILS.createTD(data.birthDate));
    $tr.append(UTILS.createTD(data.work));
    return $tr;
  }

};
