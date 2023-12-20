package com.example.amikomchat.fragment

import DBhelper
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.amikomchat.ActivitySplashLogin
import com.example.amikomchat.R
import de.hdodenhof.circleimageview.CircleImageView

class FragmentProfile : Fragment() {

    private val PICK_IMAGE_REQUEST = 1
    private lateinit var imageView: CircleImageView
    private var selectedImageUri: Uri? = null
    private lateinit var imageProfilePath: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val btnLogout: Button = view.findViewById(R.id.btnLogout)
        btnLogout.setOnClickListener {
            // Tambahkan logika logout di sini
            logout()
        }

        val fabAddImage: ImageButton = view.findViewById(R.id.fabAddImage)
        fabAddImage.setOnClickListener {
            openGallery()
        }

        // Coba memuat gambar profil dari penyimpanan lokal
        if (view != null) {
            imageView = view.findViewById(R.id.imageView2)
            loadProfileImage()
        }

        val editTextUsername: TextView = view.findViewById(R.id.editTextUsername)
        val editTextEmail: TextView = view.findViewById(R.id.editTextEmail)

        val userEmail = getLoggedInEmail()
        if (!userEmail.isNullOrEmpty()) {
            val dbHelper = DBhelper(requireContext())
            val user = dbHelper.getUser(userEmail)

            if (user != null) {
                // Tampilkan username jika tersedia
                if (!user.username.isNullOrEmpty()) {
                    editTextUsername.setText(user.username)
                }

                // Tampilkan email
                editTextEmail.setText(user.email)
            }
        } else {
            // Tangani kasus ketika pengguna tidak masuk log
        }

        // Hapus EditText yang ada untuk email
        editTextUsername.visibility = View.VISIBLE
        editTextEmail.visibility = View.VISIBLE

        return view
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("imageProfilePath", imageProfilePath)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        imageProfilePath = savedInstanceState?.getString("imageProfilePath").toString()
        if (imageProfilePath != null) {
            // Setel gambar pada ImageView
            imageView.setImageURI(Uri.parse(imageProfilePath))
        }
    }



    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data
            val imageView: CircleImageView = requireView().findViewById(R.id.imageView2)
            imageView.setImageURI(selectedImageUri)

            // Save image profile after selecting an image
            saveImageProfileToDatabase()
        }
    }

    private fun getRealPathFromURI(uri: Uri?): String {
        val cursor = context?.contentResolver?.query(uri!!, null, null, null, null)
        val index = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor?.moveToFirst()
        val path = cursor?.getString(index!!)
        cursor?.close()
        return path ?: ""
    }

    private fun saveImageProfileToDatabase() {
        val userEmail = getLoggedInEmail()
        if (!userEmail.isNullOrEmpty() && selectedImageUri != null) {
            val dbHelper = DBhelper(requireContext())
            val imageProfilePath = getRealPathFromURI(selectedImageUri)
            dbHelper.saveImageProfile(userEmail, imageProfilePath)
        } else {
            // Log an error
            Log.e("FragmentProfile", "User email or selected image URI is null or empty")
        }
    }

    private fun loadProfileImage() {
        val userEmail = getLoggedInEmail()
        if (!userEmail.isNullOrEmpty()) {
            val dbHelper = DBhelper(requireContext())
            val imageProfilePath = dbHelper.getImageProfile(userEmail)

            if (!imageProfilePath.isNullOrEmpty()) {
                imageView.setImageURI(Uri.parse(imageProfilePath))
            } else {
                // Tambahkan logging
                Log.e("FragmentProfile", "Image profile path is null or empty")
            }
        } else {
            // Tambahkan logging
            Log.e("FragmentProfile", "User email is null or empty")
        }
    }

    private fun saveLoggedInEmail(email: String) {
        Log.d("SaveLoggedInEmail", "Saving email: $email")
        val sharedPreferences = requireContext().getSharedPreferences("login_status", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("loggedInEmail", email)
        editor.apply()
    }

    private fun getLoggedInEmail(): String? {
        val sharedPreferences = requireContext().getSharedPreferences("login_status", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("loggedInEmail", null)
        Log.d("GetLoggedInEmail", "Retrieved email: $email")
        return email
    }

    private fun logout() {
        // Hapus status login dari SharedPreferences
        saveLoginStatus(false)

        // Pindah ke halaman login
        val intent = Intent(activity, ActivitySplashLogin::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun saveLoginStatus(status: Boolean) {
        val sharedPreferences = context?.getSharedPreferences("login_status", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putBoolean("isLoggedIn", status)
        editor?.apply()
    }
}
