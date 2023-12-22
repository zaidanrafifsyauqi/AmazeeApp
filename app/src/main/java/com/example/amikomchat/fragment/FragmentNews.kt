import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.amikomchat.ActivityDetailNews
import com.example.amikomchat.CardNewsAdapter
import com.example.amikomchat.NewsData
import com.example.amikomchat.R
import com.example.amikomchat.News

class FragmentNews : Fragment() {

    private lateinit var rvResep: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_news, container, false)

        rvResep = view.findViewById(R.id.rv_resep)
        rvResep.setHasFixedSize(true)
        rvResep.layoutManager = LinearLayoutManager(requireContext())
        val cardResepAdapter = CardNewsAdapter(NewsData.listData)
        rvResep.adapter = cardResepAdapter

        cardResepAdapter.setOnItemClickCallback(object : CardNewsAdapter.OnItemClickCallback {
            override fun onItemClicked(data: News) {
                showSelectedHero(data)
            }
        })

        setHasOptionsMenu(true) // Untuk menampilkan menu di action bar

        return view
    }

    private fun showSelectedHero(resep: News) {
        val intent = Intent(requireContext(), ActivityDetailNews::class.java)
        intent.putExtra("title", resep.title)
        intent.putExtra("thumb", resep.thumb)
        intent.putExtra("key", resep.key)
        intent.putExtra("times", resep.times)
//        intent.putExtra("serving", resep.serving)
//        intent.putExtra("difficulty", resep.difficulty)
        intent.putExtra("ingredient", resep.ingredient)
        intent.putExtra("step", resep.step)
        startActivity(intent)
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.action_menu_main, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.about_page -> {
//                val intent = Intent(requireContext(), AboutPage::class.java)
//                startActivity(intent)
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }
}