/*
@license
dhtmlxScheduler v.4.3.1 

This software is covered by GPL license. You also can obtain Commercial or Enterprise license to use it in non-GPL project - please contact sales@dhtmlx.com. Usage without proper license is prohibited.

(c) Dinamenta, UAB.
*/
scheduler.__recurring_template='<div class="dhx_form_repeat"> <form> <div class="dhx_repeat_left"> <label><input class="dhx_repeat_radio" type="radio" roleName="repeat" value="day" />Daglig</label><br /> <label><input class="dhx_repeat_radio" type="radio" roleName="repeat" value="week"/>Ugenlig</label><br /> <label><input class="dhx_repeat_radio" type="radio" roleName="repeat" value="month" checked />Månedlig</label><br /> <label><input class="dhx_repeat_radio" type="radio" roleName="repeat" value="year" />Årlig</label> </div> <div class="dhx_repeat_divider"></div> <div class="dhx_repeat_center"> <div style="display:none;" userId="dhx_repeat_day"> <label>Gentager sig:<br/></label> <label><input class="dhx_repeat_radio" type="radio" roleName="day_type" value="d"/>Hver</label><input class="dhx_repeat_text" type="text" roleName="day_count" value="1" />dag<br /> <label><input class="dhx_repeat_radio" type="radio" roleName="day_type" checked value="w"/>På hver arbejdsdag</label> </div> <div style="display:none;" userId="dhx_repeat_week"> Gentager sig hver<input class="dhx_repeat_text" type="text" roleName="week_count" value="1" />uge på følgende dage:<br /> <table class="dhx_repeat_days"> <tr> <td> <label><input class="dhx_repeat_checkbox" type="checkbox" roleName="week_day" value="1" />Mandag</label><br /> <label><input class="dhx_repeat_checkbox" type="checkbox" roleName="week_day" value="4" />Torsdag</label> </td> <td> <label><input class="dhx_repeat_checkbox" type="checkbox" roleName="week_day" value="2" />Tirsdag</label><br /> <label><input class="dhx_repeat_checkbox" type="checkbox" roleName="week_day" value="5" />Fredag</label> </td> <td> <label><input class="dhx_repeat_checkbox" type="checkbox" roleName="week_day" value="3" />Onsdag</label><br /> <label><input class="dhx_repeat_checkbox" type="checkbox" roleName="week_day" value="6" />Lørdag</label> </td> <td> <label><input class="dhx_repeat_checkbox" type="checkbox" roleName="week_day" value="0" />Søndag</label><br /><br /> </td> </tr> </table> </div> <div userId="dhx_repeat_month"> <label>Gentager sig:<br/></label> <label><input class="dhx_repeat_radio" type="radio" roleName="month_type" value="d"/>Hver den</label><input class="dhx_repeat_text" type="text" roleName="month_day" value="1" /> i hver<input class="dhx_repeat_text" type="text" roleName="month_count" value="1" />måned<br /> <label><input class="dhx_repeat_radio" type="radio" roleName="month_type" checked value="w"/>Den</label><input class="dhx_repeat_text" type="text" roleName="month_week2" value="1" /><select roleName="month_day2"><option value="1" selected >Mandag<option value="2">Tirsdag<option value="3">Onsdag<option value="4">Torsdag<option value="5">Fredag<option value="6">Lørdag<option value="0">Søndag</select>hver<input class="dhx_repeat_text" type="text" roleName="month_count2" value="1" />måned<br /> </div> <div style="display:none;" userId="dhx_repeat_year"> <label>Gentager sig:</label> <label><input class="dhx_repeat_radio" type="radio" roleName="year_type" value="d"/>På hver</label><input class="dhx_repeat_text" type="text" roleName="year_day" value="1" />dag i<select roleName="year_month"><option value="0" selected >Januar<option value="1">Februar<option value="2">März<option value="3">April<option value="4">Mai<option value="5">Juni<option value="6">Juli<option value="7">August<option value="8">September<option value="9">Oktober<option value="10">November<option value="11">Dezember</select><br /> <label><input class="dhx_repeat_radio" type="radio" roleName="year_type" checked value="w"/>Den</label><input class="dhx_repeat_text" type="text" roleName="year_week2" value="1" /><select roleName="year_day2"><option value="1" selected >Mandag<option value="2">Tirsdag<option value="3">Onsdag<option value="4">Torsdag<option value="5">Fredag<option value="6">Lørdag<option value="0">Søndag</select>i<select roleName="year_month2"><option value="0" selected >Januar<option value="1">Februar<option value="2">März<option value="3">April<option value="4">Mai<option value="5">Juni<option value="6">Juli<option value="7">August<option value="8">September<option value="9">Oktober<option value="10">November<option value="11">Dezember</select><br /> </div> </div> <div class="dhx_repeat_divider"></div> <div class="dhx_repeat_right"> <label><input class="dhx_repeat_radio" type="radio" roleName="end" checked/>Ingen slutdato</label><br /> <label><input class="dhx_repeat_radio" type="radio" roleName="end" />Efter</label><input class="dhx_repeat_text" type="text" roleName="occurences_count" value="1" />gentagelse<br /> <label><input class="dhx_repeat_radio" type="radio" roleName="end" />Slut</label><input class="dhx_repeat_date" type="text" roleName="date_of_end" value="'+scheduler.config.repeat_date_of_end+'" /><br /> </div> </form> </div> <div style="clear:both"> </div>';

