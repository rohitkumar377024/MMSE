package com.app.mmse.new_toolbox.tools

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.util.Log
import android.view.View
import android.widget.*
import com.app.mmse.R
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import java.io.IOException

class PlayAudioFile(ctx: Context, activity: Activity) {

    private val mCtx = ctx
    private val mActivity = activity
    private var mMediaRecorder: MediaRecorder? = null
    private var mPlayer: MediaPlayer? = null
    private lateinit var playAudioFileLayout: View
    private lateinit var labelLayout: View
    private lateinit var toolboxPlayAudioFile: ImageView
    private lateinit var recordingButton: ToggleButton
    private lateinit var playbackButton: ToggleButton
    private lateinit var playAudioFileTopLL: LinearLayout
    private lateinit var playAudioFileBottomLL: LinearLayout
    private lateinit var recordButton: Button
    private lateinit var playAudioFileDoneButton: Button
    private lateinit var playAudioFileTitleTextView: TextView
    private lateinit var playAudioFileMakeChangesLL: LinearLayout
    private lateinit var playAudioFileMakeChangesButton: Button
    private lateinit var playAudioFileSelectAudioClip: Button
    private lateinit var mFile: String
    private var isRecordingDone: Boolean = false
    private var isPlaybackDone: Boolean = false
    private val REQUEST_SELECT_AUDIO_CLIP = 123

    fun init() {
        checkPermissions()
        findViewByIds()
        showPlayAudioFileTool()
        initVisibility()
        initMediaPlayer()
        initFileLocation()
        handleSelectAudioClipButton()
        handleRecordButton()
        handleDoneButton()
        handleMakeChangesButton()
        handleRecordingAndPlaybackFeatures(mFile)
        handleRecordingButtonState()
    }

    //Handle results for Selecting Audio Clip
     fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //For Selecting Audio From Storage
        if (requestCode == REQUEST_SELECT_AUDIO_CLIP) { //1 ->> Select Audio Clip
            if (resultCode == Activity.RESULT_OK) {
                //Selected Audio Clip
                val uri = data?.data

                if (uri != null) {

                    init() //todo --> Write some documentation for here and below and this script


                    hide(playAudioFileSelectAudioClip)
                    hide(recordButton)

                    doneTitleText()

                    show(playAudioFileMakeChangesLL)

                    playAudioFileMakeChangesButton.setOnClickListener {
                        show(playAudioFileTopLL)

                        show(playAudioFileSelectAudioClip)
                        show(recordButton)

                        hide(playAudioFileMakeChangesLL)

                        defaultTitleText()
                    }
                }
            }
        }
    }

    //Title Text State Handling Functions Below
    private fun defaultTitleText() { playAudioFileTitleTextView.text = "Play an Audio File" }
    private fun doneTitleText() { playAudioFileTitleTextView.text = "Audio File Done" }

    private fun checkPermissions() { //Requesting Permission RECORD_AUDIO /* first priority */
        Permissions.check(mCtx, Manifest.permission.RECORD_AUDIO, null, object : PermissionHandler() {
            override fun onGranted() {/* no need to even show a toast here */ } }) }

    private fun findViewByIds() { /* Connecting All UI Views with their respective IDs */
        labelLayout = mActivity.findViewById(R.id.label_layout)
        playAudioFileLayout = mActivity.findViewById(R.id.play_audio_file_layout)
        toolboxPlayAudioFile = mActivity.findViewById(R.id.tool_box_play_audio_file)
        recordingButton = mActivity.findViewById(R.id.play_audio_file_recording_btn)
        playbackButton = mActivity.findViewById(R.id.play_audio_file_playback_btn)
        playAudioFileTopLL = mActivity.findViewById(R.id.play_audio_file_top_ll)
        playAudioFileBottomLL = mActivity.findViewById(R.id.play_audio_file_bottom_ll)
        recordButton = mActivity.findViewById(R.id.play_audio_file_record_audio_btn)
        playAudioFileDoneButton = mActivity.findViewById(R.id.play_audio_file_done_btn)
        playAudioFileTitleTextView = mActivity.findViewById(R.id.play_audio_file_title_txtview)
        playAudioFileMakeChangesLL = mActivity.findViewById(R.id.play_audio_file_make_changes_ll)
        playAudioFileMakeChangesButton = mActivity.findViewById(R.id.play_audio_file_make_changes_btn)
        playAudioFileSelectAudioClip = mActivity.findViewById(R.id.play_audio_file_select_audio_clip_btn)
    }

    private fun showPlayAudioFileTool() { /* Showing 'Play Audio File' Layout */
        toolboxPlayAudioFile.setOnClickListener {
            show(playAudioFileLayout)
            //todo ->> Remove this later on
            hide(labelLayout)
        }
    }

    private fun initVisibility() { /* Visibility States at Start */
        hide(playAudioFileBottomLL)   //Initially Bottom LL ->> Visibility GONE
        hide(playAudioFileMakeChangesLL)   //Initially Make Changes LL ->> Visibility GONE
    }

    private fun initMediaPlayer() {
        //Initializing the MediaPlayer ->> This change helped to fix stopping problem.
        //One instance = No problems. :)
        mPlayer = MediaPlayer()
    }

    private fun initFileLocation() { /* File location for saving the recorded audio */
        mFile = mCtx.filesDir.absolutePath + "/audio_test.3gp"
    }

    private fun handleSelectAudioClipButton() { /* Handles selecting of audio clip */
        playAudioFileSelectAudioClip.setOnClickListener {
            mActivity.startActivityForResult(selectAudioClipIntent(), REQUEST_SELECT_AUDIO_CLIP)
        }
    }

    private fun selectAudioClipIntent(): Intent { /*This function creates the intent for selecting the audio clip */
        val selectAudioClipIntent = Intent()
        selectAudioClipIntent.type = "audio/*"
        selectAudioClipIntent.action = Intent.ACTION_GET_CONTENT
        return selectAudioClipIntent
    }

    private fun handleRecordButton() { /* Handles Recording Button */
        //Once Record Button Clicked --> Top LL Visibility GONE, Bottom LL Visible
        recordButton.setOnClickListener {
            hide(playAudioFileTopLL)
            show(playAudioFileBottomLL)
        }
    }

    private fun handleDoneButton() { /* Handles Done Button */
        playAudioFileDoneButton.setOnClickListener {
            hide(playAudioFileBottomLL) //Hides Recording Button, Playback Button and Done Button
            doneTitleText()
            show(playAudioFileMakeChangesLL) //Show Make Changes LL Here
        }
    }

    private fun handleMakeChangesButton() { /* Handles Making Changes Button */
        playAudioFileMakeChangesButton.setOnClickListener {
            show(playAudioFileTopLL)
            hide(playAudioFileMakeChangesLL)
            defaultTitleText() //Change the text to default
        }
    }

    private fun handleRecordingAndPlaybackFeatures(mFile: String) { /* Handles Recording and Playback Completely */
        //Recording Audio feature
        recordingButton.setOnCheckedChangeListener { _, isChecked -> startOrStopRec(isChecked) }
        //Playback of Recorded Audio feature
        playbackButton.setOnCheckedChangeListener { _, isChecked -> playOrStop(isChecked, mFile) }
    }

    //Handles recording functionality completely
    private fun startOrStopRec(record: Boolean) {
        if (record) { startMediaRecorder() }
        else { stopMediaRecorder() }
        handleRecordingButtonState() //Handle recording state in this function
    }

    private fun startMediaRecorder() { /* Starts Media Recorder */
        mMediaRecorder = MediaRecorder()
        mMediaRecorder?.reset()
        mMediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mMediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mMediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        mMediaRecorder?.setOutputFile(mFile) //Passing File Path
        //Try-Catch
        try { mMediaRecorder?.prepare() }
        catch (e: IOException) { e.printStackTrace() }
        //Start recording
        mMediaRecorder?.start()
        isRecordingDone = false //Setting Recording State
    }

    private fun stopMediaRecorder() { /*  // Stops Media Recorder */
        if (mMediaRecorder != null) {
            mMediaRecorder?.stop()
            mMediaRecorder?.release()
            mMediaRecorder = null
            isRecordingDone = true //Setting Recording State
        }
    }


    private fun playOrStop(play: Boolean, mFile: String) { /* Handles playing and stopping of media player completely */
        try {
            if (play) {
                initMediaPlayer() //This quickfix allows us to re-start media player when user wants to re-playback
                startMediaPlayer(mFile) //Starting MediaPlayer
                isPlaybackDone = false //Setting Playback State
                mPlayer?.setOnCompletionListener { stopMediaPlayer() } /* Stopping after full audio playback */
                handlePlaybackButtonState() //Handle playback state in this function
            }
            if (!play) { stopMediaPlayer() } /* Stopping here */
        } catch (e: IOException) { Log.e("PlayAudioFileAudioUtils", e.message) }
    }

    private fun startMediaPlayer(mFile: String) { /* Starting the Media Player */
        mPlayer?.setDataSource(mFile)
        mPlayer?.prepare()
        mPlayer?.start()
    }

    private fun stopMediaPlayer() { /* Stopping the Media Player */
        mPlayer?.stop(); mPlayer?.release(); mPlayer = null
        playbackButton.isChecked = false //Changing Toggle Button State
        isPlaybackDone = true //Setting Playback State
        handlePlaybackButtonState() //Handle playback state in this function
    }

    private fun handleRecordingButtonState() { /* Handles Recording Button States */
        if (!isRecordingDone) {
            hide(playbackButton)
            hide(playAudioFileDoneButton)
        } else {
            show(playbackButton)
            show(playAudioFileDoneButton)
            recordingButton.textOff = "Re-Record Audio"
        }
    }

    private fun handlePlaybackButtonState() { /* Handles Playback Button States */
        if (!isPlaybackDone) {
            disable(recordingButton)
            disable(playAudioFileDoneButton)
        } else {
            enable(recordingButton)
            enable(playAudioFileDoneButton)
        }
    }

    private fun enable(view: View) { view.alpha = 1f; view.isClickable = true }
    private fun disable(view: View) { view.alpha = 0.4f; view.isClickable = false }
    private fun hide(view: View) { view.visibility = View.GONE }
    private fun show(view: View) { view.visibility = View.VISIBLE }

}