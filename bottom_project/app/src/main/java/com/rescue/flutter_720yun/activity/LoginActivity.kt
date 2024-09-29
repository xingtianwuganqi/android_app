package com.rescue.flutter_720yun.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.InputType
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.rescue.flutter_720yun.R
import com.rescue.flutter_720yun.databinding.ActivityLoginBinding
import com.rescue.flutter_720yun.viewmodels.LoginViewModel
import kotlinx.coroutines.launch
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.view.inputmethod.InputMethodManager
import com.google.android.material.button.MaterialButton
import com.rescue.flutter_720yun.BaseApplication


class LoginActivity : AppCompatActivity() {
    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginViewModel
    private var type: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        // 获取页面类型
        type = intent.getStringExtra("type")

        // 返回按钮
        val backBtn = binding.backButton
        backBtn?.setOnClickListener {
            finish()
        }

        // 去注册
        val registerBtn = binding.registerButton
        registerBtn?.setOnClickListener {
            val register = Intent(this, LoginActivity::class.java)
            register.putExtra("type", "registerCheckCode")
            startActivity(register)
        }

        // 去找回密码
        val findBtn = binding.findPassword
        findBtn?.setOnClickListener {
            val findPassword = Intent(this, LoginActivity::class.java)
            findPassword.putExtra("type", "findCheckCode")
            startActivity(findPassword)
        }

        // 账号输入框
        val phoneTextField = binding.username
        // 密码输入框
        val passwordTextField = binding.password
        // 登录按钮
        val loginBtn: MaterialButton = binding.login as MaterialButton
        loginBtn.setOnClickListener {
            if (phoneTextField.text.trim().isEmpty()) {
                val msg = resources.getString(R.string.login_phone_placeholder)
                Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (passwordTextField.text.trim().isEmpty()) {
                val msg = resources.getString(R.string.login_password_placeholder)
                Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (viewModel.agreement.value == false) {
                val msg = resources.getString(R.string.login_protocol)
                Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                viewModel.loginNetworking(phoneTextField.text.trim().toString(), passwordTextField.text.trim().toString())
            }

        }

        // 展示密码按钮
        val showPassword = binding.showPassword
        showPassword?.setOnClickListener {
            if (viewModel.showPassword.value == false) {
                viewModel.changeShowPasswordStatus(true)

            }else{
                viewModel.changeShowPasswordStatus(false)
            }
        }

        // 获取验证码
        val getCodeBtn = binding.getCode
        getCodeBtn?.setOnClickListener {

        }

        // 协议
        val agreementLayout = binding.agreement
        val agreementBtn = binding.agreementBtn
        agreementBtn?.setOnClickListener {
            if (viewModel.agreement.value == true) {
                viewModel.changeAgreementStatus(false)
            }else{
                viewModel.changeAgreementStatus(true)
            }
        }

        // viewModel
        viewModel.loginStatus.observe(this) {response ->
            response.let {
                if (response?.code == 200) {
                    val msg = resources.getString(R.string.login_success)
                    Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
                }else {
                    val msg = response?.message ?: resources.getString(R.string.login_fail)
                    Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
                    viewModel.cleanLoginStatus()
                }
            }
        }

        viewModel.agreement.observe(this) {
            if (it) {
                agreementBtn?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_lo_sele))
            }else{
                agreementBtn?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_lo_unse))
            }
        }

        viewModel.showPassword.observe(this) {
            if (it) {
                // 切换到明文模式
                passwordTextField.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                showPassword?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_eye_b))
            }else{
                // 切换到密码模式
                passwordTextField.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                showPassword?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_eye_o))
            }
        }

        // 点击背景取消输入框第一响应者
        val bgContainer = binding.container
        bgContainer.setOnClickListener {
            phoneTextField.clearFocus()
            passwordTextField.clearFocus()

            // 关闭软键盘（可选）
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(phoneTextField.windowToken, 0)
            imm.hideSoftInputFromWindow(passwordTextField.windowToken, 0)

        }

        // 设置富文本
        setProtocolText()

        // 更新页面显示
        // 更具type更新页面显示
        when (type) {
            "registerCheckCode", "findCheckCode" -> {
                findBtn?.visibility = View.GONE
                registerBtn?.visibility = View.GONE
                agreementLayout?.visibility = View.GONE
                loginBtn.text = resources.getText(R.string.login_check_code)
            }

            "findPassword" -> {
                findBtn?.visibility = View.GONE
                registerBtn?.visibility = View.GONE
                agreementLayout?.visibility = View.GONE
                loginBtn.text = resources.getText(R.string.login_find_password)

            }

            "register" -> {
                findBtn?.visibility = View.GONE
                registerBtn?.visibility = View.GONE
                agreementLayout?.visibility = View.GONE
                loginBtn.text = resources.getText(R.string.register_action)
            }
        }
    }

    private fun setProtocolText() {
        // 富文本
        val protocolText = binding.protocolLab
        // 创建 SpannableStringBuilder
        val spannableStringBuilder = SpannableStringBuilder()

        // 添加富文本
        val text1 = "阅读并同意"
        val text2 = "用户协议、"
        val text3 = "隐私政策"

        // 设置样式
        val spannable1 = SpannableString(text1).apply {
            setSpan(AbsoluteSizeSpan(12, true), 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) // 设置字体大小
            setSpan(
                ContextCompat.getColor(BaseApplication.context, R.color.color_node),
                0,
                length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        val spannable2 = SpannableString(text2).apply {
            setSpan(
                AbsoluteSizeSpan(12, true),
                0,
                length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            ) // 设置字体大小
//            setSpan(
//                ContextCompat.getColor(BaseApplication.context, R.color.color_system),
//                0,
//                length,
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//            )
            setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    // 点击事件处理

                }
                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = false // 添加下划线
                    ds.color = ContextCompat.getColor(BaseApplication.context, R.color.color_system)
                }
            }, 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        val spannable3 = SpannableString(text3).apply {
            setSpan(
                AbsoluteSizeSpan(12,true),
                0,
                length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) // 设置字体大小
//            setSpan(ForegroundColorSpan(
//                ContextCompat.getColor(BaseApplication.context, R.color.color_system)),
//                0,
//                length,
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//            )
            setSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    // 点击事件处理

                }
                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = false // 添加下划线
                    ds.color = ContextCompat.getColor(BaseApplication.context, R.color.color_system)
                }
            }, 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        // 拼接
        spannableStringBuilder.append(spannable1)
        spannableStringBuilder.append(spannable2)
        spannableStringBuilder.append(spannable3)

        // 设置 TextView 文本
        protocolText?.text = spannableStringBuilder
        protocolText?.movementMethod = LinkMovementMethod.getInstance() // 使点击事件生效
        protocolText?.highlightColor = Color.TRANSPARENT // 去掉高亮背景
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}