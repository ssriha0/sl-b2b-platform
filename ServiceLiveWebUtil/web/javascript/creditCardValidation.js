function validateCreditCardNumber(creditCardNumber){
	var isValidNumber = false;
	var isValid = false;
	var ccCheckRegExp = /[^\d ]/;
	var numberProduct;
	var numberProductDigitIndex;
	var checkSumTotal = 0;
	var cardNumber=null;
	
	var replaceNumbersThatMightContainACreditCard = creditCardNumber.replace(/[^0-9]/g, ' ');
	var regex16digit=replaceNumbersThatMightContainACreditCard.match("\(?:\\d{4}[ -]?){4}");
	var regex15digit=replaceNumbersThatMightContainACreditCard.match(/\d{15}/);
	var regex13digit=replaceNumbersThatMightContainACreditCard.match(/\d{13}/);
	if(regex16digit!=null){
		cardNumber=regex16digit[0];
	}else if(regex15digit!=null){
		cardNumber=regex15digit[0];
	}else if(regex13digit!=null){
		cardNumber=regex13digit[0];
	}
	if(cardNumber!=null){
		var cardNumberOnly=cardNumber.replace(/[^0-9]/g, "");
		isValidNumber = !ccCheckRegExp.test(cardNumberOnly);
		var cardNumberLength = cardNumberOnly.length;
		if (isValidNumber){
			for (digitCounter = cardNumberLength - 1; digitCounter >= 0; digitCounter--)
			{
				checkSumTotal += parseInt (cardNumberOnly.charAt(digitCounter));
				digitCounter--;
				numberProduct = String((cardNumberOnly.charAt(digitCounter) * 2));
				for (var productDigitCounter = 0;productDigitCounter < numberProduct.length; productDigitCounter++)
				{
					checkSumTotal += 
					parseInt(numberProduct.charAt(productDigitCounter));
				}
			}
			isValid = (checkSumTotal % 10 == 0);
		}
	}
	return isValid;
}