'use strict';
$(document).ready(function () {
  phoneBook.init();
});

var phoneBook = {
  lastName: null, firstName: null, workPhone: null, mobilePhone: null, mail: null, birthDate: null,

  phoneBookForm: null,

  btnSend: null, btnAdd: null, btnClear: null,

  init: function () {
    phoneBook.lastName = $("#lastName");
    phoneBook.firstName = $("#firstName");
    phoneBook.workPhone = $("#workPhone");
    phoneBook.mobilePhone = $("#mobilePhone");
    phoneBook.mail = $("#mail");
    phoneBook.birthDate = $("#birthDate");

    phoneBook.phoneBookForm = $("#phoneBookForm");

    phoneBook.btnSend = $("#btnSend");
    phoneBook.btnSend.click(phoneBook.send);
    phoneBook.btnAdd = $("#btnAdd");
    phoneBook.btnAdd.click(phoneBook.add);
    phoneBook.btnClear = $("#btnClear");
    phoneBook.btnClear.click(phoneBook.clear);

  },

  getData: function () {
    return {
      lastName: phoneBook.lastName.val(),
      firstName: phoneBook.firstName.val(),
      workPhone: phoneBook.workPhone.val(),
      mobilePhone: phoneBook.mobilePhone.val(),
      mail: phoneBook.mail.val(),
      birthDate: phoneBook.birthDate.val()
    }
  }, send: function () {
    var file = phoneBook.phoneBookForm.data("file");
    if (!file) {
      UTILS.error("Нет подготовленного файла");
      return;
    }
    var data = {
      name: file
    };
    var success = function (json) {
      var result = UTILS.getJson(json);
      if (UTILS.isError(result)) {
        return;
      }
      phoneBook.phoneBookForm.data("file", "");
      phoneBook.clearFilter();
      UTILS.success("Успешно отправлен файл: " + result.fileName);
    };
    UTILS.showWaitBlock();
    $.when(UTILS.sendRequest("/send", JSON.stringify(data), success))
      .always(UTILS.hideWaitBlock);
  },

  clear: function () {
    var file = phoneBook.phoneBookForm.data("file");
    if (!file) {
      phoneBook.clearFilter();
      return;
    }
    var data = {
      name: file
    };
    var success = function (json) {
      var result = UTILS.getJson(json);
      if (UTILS.isError(result)) {
        return;
      }
      phoneBook.phoneBookForm.data("file", "");
      phoneBook.clearFilter();
      UTILS.success("Успешно удален файл: " + result.fileName);
    };
    UTILS.showWaitBlock();
    $.when(UTILS.sendRequest("/clear", JSON.stringify(data), success))
      .always(UTILS.hideWaitBlock);
  },

  clearFilter: function () {
    phoneBook.lastName.val("");
    phoneBook.firstName.val("");
    phoneBook.workPhone.val("");
    phoneBook.mobilePhone.val("");
    phoneBook.mail.val("");
    phoneBook.birthDate.val("");
  },

  checkData: function (data) {
    if (!$.trim(data.lastName)) {
      UTILS.error("Не заполнено поле \"Фамилия\"");
      return false;
    } else {
      if (data.lastName.length > 20) {
        UTILS.error("Превышен допустимый разме поля \"Фамилия\"");
        return false;
      }
    }
    if (!$.trim(data.firstName)) {
      UTILS.error("Не заполнено поле \"Имя\"");
      return false;
    } else {
      if (data.firstName.length > 10) {
        UTILS.error("Превышен допустимый разме поля \"Имя\"");
        return false;
      }
    }
    if (!$.trim(data.workPhone)) {
      UTILS.error("Не заполнено поле \"Рабочий телефон\"");
      return false;
    }
    if (!$.trim(data.mobilePhone)) {
      UTILS.error("Не заполнено поле \"Мобильный телефон\"");
      return false;
    }
    if (!$.trim(data.mail)) {
      UTILS.error("Не заполнено поле \"e-mail\"");
      return false;
    }else{
      var reg = /^(?=.{1,41}$)(\S){1,30}@(\S){1,10}$/;
      if (!reg.test(data.mail)) {
        UTILS.error("Указан не корректный e-mail");
        return;
      }
    }
    return true;
  },

  add: function () {
    var data = phoneBook.getData();
    if (!phoneBook.checkData(data)) {
      return;
    }
    var success = function (json) {
      var result = UTILS.getJson(json);
      if (UTILS.isError(result)) {
        return;
      }
      if (!result.fileName) {
        UTILS.error("Ошибка создания файла");
        return;
      }
      phoneBook.phoneBookForm.data("file", result.fileName);
      UTILS.success("Успешно создан файл: " + result.fileName);
    };
    UTILS.showWaitBlock();
    $.when(UTILS.sendRequest("/add", JSON.stringify(data), success))
      .always(UTILS.hideWaitBlock);
  }

};
