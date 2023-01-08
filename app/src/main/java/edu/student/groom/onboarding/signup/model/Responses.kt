package edu.student.groom.onboarding.signup.model

class Responses {
     data class Institute(val id:Int,val name:String)

    data class InstituteResponse(val institutes:List<Institute> )
}