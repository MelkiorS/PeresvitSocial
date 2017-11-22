$(function(){
    function showChapters() {
        $('#chapter').children("option").each(function() {
            $(this).attr('hidden', $('#groupName').val()!=$(this).attr("rgt"));
        })
    }

    $('.category-name span').on('click',function(){
        console.log($(this).parent().parent().find($('ol .category-item')));
        $(this).parent().parent().find($('.category-item')).toggle(600);
    });
    $( document ).ready(function() {
        showChapters();
    });
    $('#groupName').on('change',function(){
        showChapters();
        $('#chapter').val($('#chapter').children("option[hidden!=hidden]").first().val());
    });
});