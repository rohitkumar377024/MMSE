package com.app.mmse.numbers.five_to_one_reverse

//This class helps in checking the reverse logic 5 4 3 2 1
class ReverseLogic {
    //This function checks if the input is 5 4 3 2 1 or not
    fun checkReverseLogic(voice_results: String) : Boolean {
        //Value that is returned from the function
        var reverse = true

        //If result less than or greater than 5 numbers, obviously ->> reverse != 5 to 1
        if (voice_results.length < 5 || voice_results.length > 5) {
            reverse = false
        }

        //If result is 5 numbers
        if (voice_results.length == 5) {
            //Then check if it is reverse 5 to 1 ->> 5 4 3 2 1
            if ((voice_results[0] != '5') || (voice_results[1] != '4')
                || (voice_results[2] != '3') || (voice_results[3] != '2') || (voice_results[4] != '1')) {
                reverse = false
            }
            //todo -> also check for spacing if exists later
        }

        //Returning the boolean value
        return reverse
    }

}
