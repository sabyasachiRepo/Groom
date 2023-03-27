package edu.student.groom.onboarding.signup.model

class Requests {

    data class SignUpRequest(
        val firstName: String,
        val lastName: String,
        val email: String,
        val instituteId: Long,
        val phoneNumber: String,
        val password: String
    )

}