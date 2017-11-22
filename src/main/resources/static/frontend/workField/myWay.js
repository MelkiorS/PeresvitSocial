'use strict';
function show_myWayBasic(){
    // var private_office = $($('link[rel=import].private_office').prop('import')).find('.show').get(0);
    $('.main_section').load('myWay/myWayBasic');
    //при загрузке делаем кнопку 'Особистий кабінет' активной
    for(var i in $('.my_way_sidebar_group_button')){
        if($.trim($('.my_way_sidebar_group_button')[i].innerHTML) =='Базова техніка'){
            $($('.my_way_sidebar_group_button')[i]).addClass('active_button');
        }
    }
}
$(function() {

    show_myWayBasic

    // $('.my_way_sidebar_group_button').on('click', function () {
    //     $(this).addClass('active').removeClass('no_active');
    // });
    $('.my_way_sidebar_group_button').on('click', function () {
        $(this).addClass('active').removeClass('no_active');
        console.log($.trim($(this).html()));
        $('.my_way_sidebar_group_button').not($(this)).addClass('no_active').removeClass('active');
        $('.main_section').empty();
        if ($.trim($(this).html()) === 'Базова техніка') {
            $('.main_section').load('myWay/myWayBasic');
        }
        if ($.trim($(this).html()) === 'Базові технічні комплекси') {
            $('.main_section').load('');
        }
        if ($.trim($(this).html()) === 'Парна робота') {
            $('.main_section').load('myWay/myWayPairWork');
        }
        if ($.trim($(this).html()) === 'Спеціальна фізпідготовка') {
            $('.main_section').load('myWay/myWaySpecPhysical');
        }
        if ($.trim($(this).html()) === 'Загальна фізпідготовка') {
            $('.main_section').load('myWay/myWayGeneralPhysical');
        }
        if ($.trim($(this).html()) === 'Розбивання предметів') {
            $('.main_section').load('myWay/myWayBreakingObj');
        }


    });
});
