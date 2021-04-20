package com.ben.taskplanner.view.credentials.verification_code

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ben.taskplanner.R
import com.ben.taskplanner.databinding.FragmentVerificationCodeBinding
import com.ben.taskplanner.interfaces.VerificationTokenListener
import com.ben.taskplanner.model.ResetPasswordParcelModel
import com.ben.taskplanner.model.ResetPasswordResponse
import com.ben.taskplanner.model.ResponseHandler
import com.ben.taskplanner.repository.UserRepository
import com.ben.taskplanner.util.CustomDialog
import com.ben.taskplanner.util.ExtensionFunctions.makeSnackBar
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.ResponseBody
import javax.inject.Inject


@AndroidEntryPoint
class VerificationCodeFragment : Fragment(), VerificationTokenListener {

    @Inject lateinit var userRepository: UserRepository
    @Inject lateinit var gson: Gson

    lateinit var binding: FragmentVerificationCodeBinding
    private lateinit var verificationCodeViewModel: VerificationCodeViewModel

    private val args: VerificationCodeFragmentArgs by navArgs()

    private lateinit var verificationCodeFragmentFactory: VerificationCodeFragmentFactory

    private val customDialog: CustomDialog by lazy { CustomDialog(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVerificationCodeBinding.inflate(inflater)
        verificationCodeFragmentFactory = VerificationCodeFragmentFactory(userRepository)
        verificationCodeViewModel = ViewModelProvider(this, verificationCodeFragmentFactory).get(VerificationCodeViewModel::class.java)
        verificationCodeViewModel.verificationTokenListener = this
        binding.lifecycleOwner = viewLifecycleOwner
        binding.verificationCodeViewModel = verificationCodeViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Tag", "email: ${args.email}")
    }

    override fun onValidate(token: String) {
        verificationCodeViewModel.authorizeVerificationToken(args.email, token) {
            when(it) {
                is ResponseHandler.SuccessResponse -> {
                    CustomDialog.dismissDialog()
                    val action = VerificationCodeFragmentDirections.actionVerificationCodeFragmentToResetPasswordFragment(
                        ResetPasswordParcelModel(args.email, token)
                    )
                    findNavController().navigate(action)
                }
                is ResponseHandler.FailureResponse -> {
                    CustomDialog.dismissDialog()
                    requireView().makeSnackBar(onConvertResponseToModelClass<ResetPasswordResponse>(it.responseBody)!!.message, "Retry") {
                        findNavController().navigate(R.id.action_verificationCodeFragment_to_forgotPasswordFragment)
                    }
                }
                is ResponseHandler.Loading -> {
                    customDialog.showDialog("Verifying the 6-digit code...")
                }
            }
        }
    }

    private inline fun <reified C> onConvertResponseToModelClass(responseBody: ResponseBody?): C? {
        return gson.fromJson(responseBody?.string(), C::class.java)
    }
}