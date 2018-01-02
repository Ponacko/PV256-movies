package cz.muni.fi.pv256.movio2.uco_433419.list

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.muni.fi.pv256.movio2.uco_433419.ACTION_DOWNLOAD_DATA
import cz.muni.fi.pv256.movio2.uco_433419.R
import cz.muni.fi.pv256.movio2.uco_433419.data.FilmManager
import cz.muni.fi.pv256.movio2.uco_433419.detail.DetailActivity
import cz.muni.fi.pv256.movio2.uco_433419.detail.DetailFragment
import cz.muni.fi.pv256.movio2.uco_433419.download.DownloadService
import cz.muni.fi.pv256.movio2.uco_433419.model.Film
import cz.muni.fi.pv256.movio2.uco_433419.model.ListCategory
import cz.muni.fi.pv256.movio2.uco_433419.model.ListItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_list.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


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


    private var filmTask: FilmTask? = FilmTask(this)

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


    private var listItems: ArrayList<ListItem> = ArrayList()

    private fun addListToAdapter(filmList: ArrayList<Film>) {
        val cal = Calendar.getInstance()
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val today = formatter.format(cal.time)
        val upcoming = filmList.filter { film -> film.release_date > today }
        val current = filmList.filterNot { film -> film.release_date > today }
        listItems = arrayListOf<ListItem>()
                .plus(ListCategory(getString(R.string.category_upcoming)))
                .plus(upcoming.sortedByDescending { film -> film.release_date })
                .plus(ListCategory(getString(R.string.category_current)))
                .plus(current.sortedByDescending { film -> film.release_date }) as ArrayList<ListItem>
        switchToNetwork()
    }

    fun switchToNetwork() {
        val adapter = FilmAdapter(listItems, this)
        if (listItems.isEmpty()) {
            setEmptyScreen()
        }
        list.adapter = adapter
    }

    private fun setEmptyScreen() {
        list?.visibility = View.GONE
        empty?.visibility = View.VISIBLE
    }

    private fun setListScreen() {
        list?.visibility = View.VISIBLE
        empty?.visibility = View.GONE
    }

    private lateinit var manager: FilmManager

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        list.adapter = FilmAdapter(arrayListOf(), this)

        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) =
                    processResults(intent.getParcelableArrayListExtra("result"))
        }
        list.layoutManager = LinearLayoutManager(context)
        val intentFilter = IntentFilter(ACTION_DOWNLOAD_DATA)
        context?.registerReceiver(receiver, intentFilter)
        val intent = Intent(context, DownloadService::class.java)
        context?.startService(intent)
        manager = FilmManager(context!!)
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    fun switchToDatabase() {
        val films = manager.getFilms()
        val adapter = if (films.isNotEmpty()) FilmAdapter(films as ArrayList<ListItem>, this)
        else FilmAdapter(arrayListOf(), this)
        setListScreen()
        list.adapter = adapter
    }


    fun startFilmDetailActivity(film: Film) {
        if (detailFragmentTablet != null && (detailFragmentTablet as DetailFragment).titleText != null) {
            val detail = detailFragmentTablet as DetailFragment
            detail.setFilmProperties(film)
        } else {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra("FILM", film)
            startActivity(intent)
        }
    }

    private fun processResults(result: ArrayList<Film>) {
        if (result.isNotEmpty()) {
            addListToAdapter(result)
        } else {
            setEmptyScreen()
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
