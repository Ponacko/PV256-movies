package cz.muni.fi.pv256.movio2.uco_433419

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_list.*
import android.util.Log
import com.squareup.okhttp.Callback
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import com.squareup.okhttp.internal.Internal.logger
import java.io.IOException
import java.util.logging.Level
import com.google.gson.GsonBuilder


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : android.support.v4.app.Fragment() {

    private var mListener: OnFragmentInteractionListener? = null
    private var mHandler: Handler = Handler()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =// Inflate the layout for this fragment
            inflater.inflate(R.layout.fragment_list, container, false)

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    fun addResponseToFilmList(response: FilmResponse) {
        val filmList = response.results as ArrayList<ListItem>
        val adapter = FilmAdapter(filmList, this)
        list.layoutManager = LinearLayoutManager(context)
        if (filmList.isEmpty()){
            list.visibility = View.GONE
            empty.visibility = View.VISIBLE
        }
        list.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val task = FilmTask(this)
        task.execute()
//        if (filmList.isEmpty()) {
//            list.visibility = View.GONE
//            empty.visibility = View.VISIBLE
//        }

    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    fun startFilmDetailActivity(film : Film) {
        if (detailFragmentTablet != null && (detailFragmentTablet as DetailFragment).titleText != null) {
            val detail = detailFragmentTablet as DetailFragment
            detail.setFilmText(film)
        } else {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra("FILM", film)
            startActivity(intent)
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): ListFragment {
            val fragment = ListFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
