package edu.student.groom.onboarding.signup.model

class Responses {
     data class BaseResponse(val status:String,val message:String)

     data class Institute(val id:Long,val name:String)

     data class InstituteResponse(val institutes:List<Institute> )
}