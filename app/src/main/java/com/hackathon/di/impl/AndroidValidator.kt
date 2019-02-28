package com.hackathon.di.impl

import com.hackathon.di.IValidator

class AndroidValidator : IValidator {
    override fun validateName(name: String): Boolean {
        return name.trim().isNotEmpty()
    }
}