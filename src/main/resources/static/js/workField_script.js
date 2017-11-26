'use strict';
$(function(){
    //меняем Имя
    $('.pencil-name').on('click',function(){

        var $name = $('.allName'),children = $name.children();
        var $agreeButton = $('<button type="submit" class="btn btn-success pensil-button"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span></button>');
        var $disagreeButton = $('<button type="reset" class="btn btn-danger pensil-button"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></input>');
        children.css('display','none');
        //Тут создаем форму и на нее нужно навесить какой то action для имени
        var formForChange = $('<form action="#" class="formForChange"></form>');
        var textName = $('<span>Ваше ПІБ</span>');
        var InputFirstName = $('<input type="text" class="form-control" placeholder="Iм`я">');
        var InputLastName = $('<input type="text" class="form-control" placeholder="Прізвище">');
        var InputMiddleName = $('<input type="text" class="form-control" placeholder="По батькові">');
        // var userName = (children.eq(0).html()+' '+children.eq(1).html()+children.eq(2).html()).toString();
        InputLastName.attr('value',children.eq(0).html());
        InputFirstName.attr('value',children.eq(1).html());
        InputMiddleName.attr('value',children.eq(2).html());

        formForChange.append(textName, InputLastName, InputFirstName,InputMiddleName,$agreeButton,$disagreeButton);

        $name.append(formForChange);
        InputLastName.on('keyup',function(){
            if($(this).val() == ''){
                $agreeButton.attr('disabled',true);
            }else{
                $agreeButton.attr('disabled',false);
            }
        });
        $disagreeButton.on('click',function(){
            formForChange.remove();
            children.css('display','inline');
        });
    });
    //Меняем Город
    $('.pencil-info-city').on('click',function(){
        var self =$(this),city = self.parent(),children = city.children();
        var $agreeButton = $('<button type="submit" class="btn btn-success pensil-button"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span></button>');
        var $disagreeButton = $('<button type="reset" class="btn btn-danger pensil-button"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></input>');

        //Тут создаем форму и на нее нужно навесить какой то action для отправки города
        var formForChange = $('<form action="#" class="formForChange"></form>');


        var citySelect = $('<select class="form-control" th:field="*{city.cityId}" id="cityList" >' +
            '<option disabled value="" th:text="">Місто</option>' +
            '<option th:each="city : ${cityList}" th:value="${city.cityId}"th:text="${city.cityName}"></option>' +
            '</select>');
        var select = $('<select class="form-control"><option value="">qwerty</option><option value="123456">qwerty11</option></select>');

        children.css('display','none');
        formForChange.append(citySelect,$agreeButton,$disagreeButton);
        city.append(formForChange);
        $disagreeButton.on('click',function(){
            formForChange.remove();
            children.css('display','inline');
        });
    });
    //Меняем Клуб
    $('.pencil-info-club').on('click',function(){
        var self =$(this),club = self.parent(),children = club.children();
        var $agreeButton = $('<button type="submit" class="btn btn-success pensil-button"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span></button>');
        var $disagreeButton = $('<button type="reset" class="btn btn-danger pensil-button"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></input>');

        //Тут создаем форму и на нее нужно навесить какой то action для отправки клуба
        var formForChange = $('<form action="#" class="formForChange"></form>');


        var clubSelect = $('<select id="clubList" th:field="*{club.clubId}" class="form-control">' +
            '<option disabled value="" th:text="">Клуб</option>' +
            '<option th:each="club : ${clubList}" th:value="${club.clubId}"th:text="${club.clubName}"></option>' +
            '</select>');
        var select = $('<select class="form-control"><option value="">club</option><option value="123456">qwerty11</option></select>');

        children.css('display','none');
        formForChange.append(clubSelect,$agreeButton,$disagreeButton);
        club.append(formForChange);
        $disagreeButton.on('click',function(){
            formForChange.remove();
            children.css('display','inline');
        });
    });
    //Меняем направление
    $('.pencil-info-direct').on('click',function(){
        var self =$(this),direct = self.parent(),children = direct.children();
        var $agreeButton = $('<button type="submit" class="btn btn-success pensil-button"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span></button>');
        var $disagreeButton = $('<button type="reset" class="btn btn-danger pensil-button"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></input>');

        //Тут создаем форму и на нее нужно навесить какой то action для отправки направления
        var formForChange = $('<form action="#" class="formForChange"></form>');


        var directSelect = $('<select th:field="*{combatArt.combatArtId}" id="combatArtList" class="form-control" >' +
            '<option disabled value="" th:text="">Направлення</option>' +
            '<option th:each="combatArt : ${combatArtList}" th:value="${combatArt.combatArtId}"th:text="${combatArt.combatArtName}"></option>' +
            '</select>');

        var select = $('<select class="form-control"><option value="">direct</option><option value="123456">qwerty11</option></select>');

        children.css('display','none');
        formForChange.append(directSelect,$agreeButton,$disagreeButton);
        direct.append(formForChange);
        $disagreeButton.on('click',function(){
            formForChange.remove();
            children.css('display','inline');
        });
    });
    //Меняем информацию о себе
    $('.pencil-info-me').on('click',function(){
        var self =$(this),city = self.parent(),children = city.children();
        var $agreeButton = $('<button type="submit" class="btn btn-success pensil-button"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span></button>');
        var $disagreeButton = $('<button type="reset" class="btn btn-danger pensil-button"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span></input>');

        //Тут создаем форму и на нее нужно навесить какой то action для отправки данных о себе
        var formForChange = $('<form action="#" class="formForChange"></form>');

        var userText = (children.eq(1).html()).toString();
        var inputText = $('<textarea th:field="*{about}" id="about" class="form-control" rows="2">'+userText+'</textarea>');


        children.css('display','none');
        formForChange.append(inputText,$disagreeButton, $agreeButton);
        city.append(formForChange);
        $disagreeButton.on('click',function(){
            formForChange.remove();
            children.css('display','inline' );
        });
    });

});

