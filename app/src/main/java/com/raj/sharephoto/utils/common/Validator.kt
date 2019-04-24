package com.raj.sharephoto.utils.common

import android.util.Patterns
import com.raj.sharephoto.R

object Validator {

    private const val MIN_PASSWORD_LENGTH = 6

    private const val MIN_NAME_LENGTH = 3

    fun validateEmailPassword( email:String?, password:String?):ArrayList<Validation> =

        ArrayList<Validation>().apply {

            when{

                email.isNullOrEmpty()->
                    add(Validation(Validation.Field.EMAIL, Resource.error(R.string.prompt_email)))

                 !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                    add(Validation(Validation.Field.EMAIL, Resource.error(R.string.error_invalid_email)))

                else->{
                    add(Validation(Validation.Field.EMAIL, Resource.success()))

                }
            }


            when{

                password.isNullOrEmpty()->
                    add(Validation(Validation.Field.PASSWORD, Resource.error(R.string.prompt_password)))
                password.length<MIN_PASSWORD_LENGTH ->
                    add(Validation(Validation.Field.PASSWORD, Resource.error(R.string.error_invalid_password)))

                else->{
                    add(Validation(Validation.Field.PASSWORD, Resource.success()))

                }
            }





        }


    fun validateEmailPasswordName( email:String?, password:String?, name:String?):ArrayList<Validation> =

        ArrayList<Validation>().apply {

            when{

                email.isNullOrEmpty()->
                    add(Validation(Validation.Field.EMAIL, Resource.error(R.string.prompt_email)))

                !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                    add(Validation(Validation.Field.EMAIL, Resource.error(R.string.error_invalid_email)))

                else->{
                    add(Validation(Validation.Field.EMAIL, Resource.success()))

                }
            }


            when{

                password.isNullOrEmpty()->
                    add(Validation(Validation.Field.PASSWORD, Resource.error(R.string.prompt_password)))
                password.length<MIN_PASSWORD_LENGTH ->
                    add(Validation(Validation.Field.PASSWORD, Resource.error(R.string.error_invalid_password)))

                else->{
                    add(Validation(Validation.Field.PASSWORD, Resource.success()))

                }
            }


            when{

                name.isNullOrEmpty()->
                    add(Validation(Validation.Field.NAME, Resource.error(R.string.prompt_name)))
                name.length< MIN_NAME_LENGTH ->
                    add(Validation(Validation.Field.NAME, Resource.error(R.string.error_field_required)))

                else->{
                    add(Validation(Validation.Field.NAME, Resource.success()))

                }
            }





        }




    data class Validation(val field:Field,val resource: Resource<Int>){

        enum class Field {
            EMAIL,
            PASSWORD,
            NAME
        }

    }

}