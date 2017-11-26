$(function(){
    if(window.innerWidth <768){
        let sideBarWidth = window.innerWidth*0.865;
        $('.toggle-button').sideNav({
                menuWidth: sideBarWidth , // Default is 300
                edge: 'left', // Choose the horizontal origin
                closeOnClick: true, // Closes side-nav on <a> clicks, useful for Angular/Meteor
                draggable: true // Choose whether you can drag to open on touch screens
            }
        );
        $('.bxslider').bxSlider({
            // slideWidth: 182,
            slideWidth: 365,
            maxSlides: 1,
            minSlides: 1,
            slideMargin: 5,
            pager: false,
            adaptiveHeight: true,
            captions: true,
        });
    }

    $('.sideNav .menu-level1 .btn-my-way').hover(function(){
        $('.sideNav .menu-level1 .btn-my-way img').attr('src','/resources/frontend/image/red-arrow.png');},function(){
        $('.sideNav .menu-level1 .btn-my-way img').attr('src','/resources/frontend/image/blue-arrow.png');

    });

    $('.my-way-main .main-article-item  h3').on('click',function(){
    $(this).parent().children('ol').fadeToggle();
    $(this).parent().toggleClass('level1-active');
    console.log($(this));
});
    if(window.innerWidth >767) {
        $('.sideNav .menu-level1 li .btn-my-way ').on('click', function () {
            $('.sideNav .menu-level1 .btn-my-way img').toggleClass('transformed', 'slow');
            $('.menu-level2').fadeToggle(100);
            $('.sideNav .menu-level1 .my-way').toggleClass('border-my-way', 100);
        });
        if($('.main-article-item')){$('.main-article-item').first().css('margin-top','3.8%')}
    }
    if($('.bxslider')){
        $('.bxslider').bxSlider({
            // slideWidth: 182,
            slideWidth: 203,
            maxSlides: 3,
            minSlides: 1,
            slideMargin: 20,
            pager: false,
            adaptiveHeight: true,
            captions: true,
        });
    }

});
