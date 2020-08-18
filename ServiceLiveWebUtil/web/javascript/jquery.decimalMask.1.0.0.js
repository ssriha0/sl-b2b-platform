/**
 * Decimal Mask Plugin
 * 
 * @version 1.0.0
 * 
 * @licensed MIT <see below>
 * @licensed GPL <see below>
 * 
 * @requires jQuery 1.4.x
 * @requires jQuery.caret.1.02.min.js
 * 
 * @author Stéfano Stypulkowski
 */
/**
 * MIT License
 * Copyright (c) 2010 Stéfano Stypulkowski
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy 
 * of this software and associated documentation files (the "Software"), to deal 
 * in the Software without restriction, including without limitation the rights 
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell 
 * copies of the Software, and to permit persons to whom the Software is 
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
/**
 * GPL LIcense
 * Copyright (c) 2010 Stéfano Stypulkowski
 * 
 * This program is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License 
 * for more details.
 * 
 * You should have received a copy of the GNU General Public License along 
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 */
(function($){
	
	$.fn.decimalMask = function(options){
		//changed the default for Service Live from (',',4,8)
		var configs = {
				decimalSeparator : ".",
				decimalLength : 2,
				integerLength : 10
			};
		
		configs = $.extend(configs,options);
		
		var s = configs.decimalSeparator.substring(0,1);
		
		var intL = configs.integerLength;

		var decL = configs.decimalLength;

		$(this).each(function(){
			
			if ($(this).attr("id") === ""){
				var id;
				do{
					id = "input" + Math.floor(Math.random()*10001);
				}while($("#" + id).size() !== 0);
				$(this).attr("id",id);
			}
		});
		
		$(this).each(function(){
			$(this).attr("maxlength", (intL + decL + 1));
			
			var value = $(this).val();
	
			value = value.replace(".",s);
	
			if ( value.indexOf(s) === 0){
				value = "0" + value;
			}
			$(this).val(value);
		});
		
		var inputHandler = function(event){
			
			var id = "#" + this.id;
			
			var value = $(id).val();
		
			var newValue = new String();
			
			var haveSep = value.indexOf(s) > -1 ? true : false;
			
			for (var i =0 ; i < value.length ; i++){
				
				if (value.substring(i,i+1).match("[0-9," + s + "]")){
					if (value.substring(i,i+1) == s && newValue.indexOf(s) == -1){
						newValue = newValue + value.substring(i,i+1);  
					}
					if (value.substring(i,i+1) != s){
						newValue = newValue + value.substring(i,i+1);
					}
				}
			}
			
			var parts = newValue.split(s);
			
			if ( parts[1] > decL ){
				
				parts[1] = parts[1].substring(0,decL);
			}
			
			if (parts[0] > intL){
				
				parts[0] = parts[0].substring((parts[0].length-intL),parts[0].length);
			}
			$(id).val((parts[0] === undefined ? "" : parts[0] ) + (haveSep ? s : "") + (parts[1] === undefined ? "" : parts[1]));
		};
		
		var keyDownHandler = function(event){
			
			var id = "#" + this.id;
			
			var k = event.keyCode;
			
			if ( (event.shiftKey || event.ctrlKey)){

				/*home,end,arrows,a,z,x,c,v*/
				if ( (k >= 34 && k <= 40) || k === 65 || k === 90 || k === 88 || k === 67 || k === 86 ){
					
					return true;
				}
				return false;
			}

			if (event.altKey ){
				return false;
			}
			/*home,end,tab*/
			if ( k === 35 || k === 36 || k === 9 ){
				return true;
			}
			/*arrows*/	
			if ( k >= 36 && k <= 40 ){
				return true;
			}
			/*delete*/
			if ( k === 46 ){

				var value = $(id).val();
				
				if ( $(id).caret().start == $(id).val().indexOf(s) && $(id).caret().start == $(id).caret().end && value.length > 1 && value.indexOf(s) > 0 && value.indexOf(s)+1 < value.length){
					return false;
				}

				if ( value.indexOf(s) >= $(id).caret().start && value.indexOf(s) < $(id).caret().end ){
					$(id).val(value.substring(0,$(id).caret().start) + s + value.substring($(id).caret().end,value.length));
					if ( $(id).val().length === 1){
						$(id).val("");
					}
					return false;
				}
				return true;

			}

			/*backspace*/
			if ( k === 8 ){

				var value = $(id).val();
				
				if ( $(id).caret().start == value.indexOf(s) + 1 && $(id).caret().start == $(id).caret().end && value.length > 1 && value.indexOf(s) > 0 && value.indexOf(s)+1 < value.length){
					return false;
				}

				if ( value.indexOf(s) >= $(id).caret().start && value.indexOf(s) < $(id).caret().end ){
					$(id).val(value.substring(0,$(id).caret().start) + s + value.substring($(id).caret().end,value.length));
					if ( $(id).val().length === 1){
						$(id).val("");
					}
					return false;
				}				
				return true;
			}
			/*188,110 = , 
			 *194,190 = . 
			 **/
			if ( k === 188 || k === 190 || k === 194 || k === 110){
				
				if (s === "," && (k === 190 || k === 194) ){
					return false;
				}
				if (s === "." && k === 188 ){
					return false;
				}
				if (s === "," && (k === 188 || k === 110) && $(id).val().indexOf(",") > -1 ){
					return false;
				}
				if (s === "." && (k === 190 || k === 194) && $(id).val().indexOf(".") > -1 ){
					return false;
				}
				return true;
			}
			/*numbers*/
			if ( (k >= 96 && k <= 105) || (k >= 48 && k <= 57) ){
				
				if ($(id).val().length > (intL + decL + 1)){
					return false;
				}

				var value = $(id).val();
				
				var parts = value.split(s);

				if ( $(id).caret().start == $(id).caret().end ){
					
					if ( $(id).caret().start > value.indexOf(s) && value.indexOf(s) > -1 && parts[1].length >= decL){
						
						return false;
					}
					if (( $(id).caret().start <= value.indexOf(s) || value.indexOf(s) == -1) && parts[0].length >= intL){
						
						return false;
					}
				}else{
					
					if ($(id).caret().end - $(id).caret().start == value.length ){
						
						return true;
					}
					if ($(id).caret().start <= value.indexOf(s) && $(id).caret().end > value.indexOf(s) ){
						
						return false;
					}
				}
				return true;
			}
			return false;
		};

		$(this).bind("input",inputHandler);
		$(this).bind("keydown",keyDownHandler);
	};
})(jQuery);