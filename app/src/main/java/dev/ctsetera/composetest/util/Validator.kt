package dev.ctsetera.composetest.util

object Validator {

    // 必須入力の検証
    fun isRequired(input: String?): Boolean {
        return !input.isNullOrEmpty()
    }

    // 文字数がminLength文字より大きいか
    fun isLengthGreaterThan(input: String?, minLength: Int): Boolean {
        return (input?.length ?: 0) > minLength
    }

    // 文字数がminLength文字以上か
    fun isLengthGreaterThanOrEqual(input: String?, minLength: Int): Boolean {
        return (input?.length ?: 0) >= minLength
    }

    // 文字数がmaxLength文字より小さいか
    fun isLengthLessThan(input: String?, maxLength: Int): Boolean {
        return (input?.length ?: 0) <= maxLength
    }

    // 文字数がmaxLength文字以下か
    fun isLengthLessThanOrEqual(input: String?, maxLength: Int): Boolean {
        return (input?.length ?: 0) <= maxLength
    }

    // 文字数がminLengthとmaxLengthの間か
    fun isLengthInRange(input: String?, minLength: Int, maxLength: Int): Boolean {
        return (input?.length ?: 0) <= maxLength
    }

    // 文字列がアルファベット・英数字・アンダースコアのみで構成されているか
    fun isAlphanumericUnderscore(input: String?): Boolean {
        return input?.matches(Regex("^[a-zA-Z0-9_]*$")) ?: false
    }
}
