$(function(){
    var basicTechnique= $('<div class="btn-group " role="group">' +
        '<form action="/resource/myWay/1">' +
        '<button class="btn btn-default btn-lg dropdown-toggle control_button " type="submit"  aria-haspopup="true" aria-expanded="false">Базова Техніка</button>' +
        '</form></div>');
    var basicTechComplex= $('<div class="btn-group " role="group">' +
        '<form action="/resource/myWay/2">' +
        '<button class="btn btn-default btn-lg dropdown-toggle control_button " type="submit"  aria-haspopup="true" aria-expanded="false">Базова тех комплекси</button>' +
        '</form></div>');
    var workInPairs= $('<div class="btn-group " role="group">' +
        '<form action="/resource/myWay/3">' +
        '<button class="btn btn-default btn-lg dropdown-toggle control_button " type="submit"  aria-haspopup="true" aria-expanded="false">Робота в парі</button>' +
        '</form></div>');
    var specTraining= $('<div class="btn-group " role="group">' +
        '<form action="/resource/myWay/4">' +
        '<button class="btn btn-default btn-lg dropdown-toggle control_button " type="submit"  aria-haspopup="true" aria-expanded="false">Спецпідготовка</button>' +
        '</form></div>');
    var generalTraining= $('<div class="btn-group " role="group">' +
        '<form action="/resource/myWay/5">' +
        '<button class="btn btn-default btn-lg dropdown-toggle control_button " type="submit"  aria-haspopup="true" aria-expanded="false">Загальна фізпідготовка</button>' +
        '</form></div>');
    var generalTraining= $('<div class="btn-group " role="group">' +
        '<form action="/resource/myWay/5">' +
        '<button class="btn btn-default btn-lg dropdown-toggle control_button " type="submit"  aria-haspopup="true" aria-expanded="false">Загальна фізпідготовка</button>' +
        '</form></div>');
    var breakingItems= $('<div class="btn-group " role="group">' +
        '<form action="/resource/myWay/6">' +
        '<button class="btn btn-default btn-lg dropdown-toggle control_button " type="submit"  aria-haspopup="true" aria-expanded="false">Розбивання предметів</button>' +
        '</form></div>');

    $('.myWayButton').after(basicTechnique,basicTechComplex,workInPairs,specTraining,generalTraining,breakingItems);
});