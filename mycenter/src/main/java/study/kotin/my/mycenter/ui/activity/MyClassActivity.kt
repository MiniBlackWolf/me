package study.kotin.my.mycenter.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import kotlinx.android.synthetic.main.myclass_main.*
import org.jetbrains.anko.startActivityForResult
import study.kotin.my.baselibrary.ui.activity.BaseMVPActivity
import study.kotin.my.mycenter.R
import study.kotin.my.mycenter.injection.commponent.DaggerMyCommponent
import study.kotin.my.mycenter.injection.module.Mymodule
import study.kotin.my.mycenter.persenter.Mypersenter
import java.util.ArrayList

class MyClassActivity : BaseMVPActivity<Mypersenter>() {
    //星期几
    private var day: RelativeLayout? = null

    //SQLite Helper类
    private val databaseHelper = DatabaseHelper(this, "database.db", null, 1)

    internal var currentCoursesNumber = 0
    internal var maxCoursesNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.myclass_main)
        initinject()
        //工具条
        //从数据库读取数据
        loadData()
        createLeftView()
        addclass.setOnClickListener {
            startActivityForResult<AddCourseActivity>(1)
        }
    }

    //从数据库加载数据
    private fun loadData() {
        val coursesList = ArrayList<Course>() //课程列表
        val sqLiteDatabase = databaseHelper.writableDatabase
        val cursor = sqLiteDatabase.rawQuery("select * from courses", null)
        if (cursor.moveToFirst()) {
            do {
                coursesList.add(Course(
                        cursor.getString(cursor.getColumnIndex("course_name")),
                        cursor.getString(cursor.getColumnIndex("teacher")),
                        cursor.getString(cursor.getColumnIndex("class_room")),
                        cursor.getInt(cursor.getColumnIndex("day")),
                        cursor.getInt(cursor.getColumnIndex("class_start")),
                        cursor.getInt(cursor.getColumnIndex("class_end"))))
            } while (cursor.moveToNext())
        }
        cursor.close()

        //使用从数据库读取出来的课程信息来加载课程表视图
        for (course in coursesList) {
            createLeftView()
            createCourseView(course)
        }
    }

    //保存数据到数据库
    private fun saveData(course: Course) {
        val sqLiteDatabase = databaseHelper.writableDatabase
        sqLiteDatabase.execSQL("insert into courses(course_name, teacher, class_room, day, class_start, class_end) " + "values(?, ?, ?, ?, ?, ?)",
                arrayOf(course.courseName, course.teacher, course.classRoom, course.day.toString() + "", course.start.toString() + "", course.end.toString() + "")
        )
    }

    //创建课程节数视图
    private fun createLeftView() {

        if(maxCoursesNumber<7) {
            for (i in 0..7) {
                val view = LayoutInflater.from(this).inflate(R.layout.left_view, null)
                val params = LinearLayout.LayoutParams(110, 180)
                view.layoutParams = params

                val text = view.findViewById<TextView>(R.id.class_number_text)
                if (i == 7) {
                    text.text = "自习"
                } else {
                    text.text = (++currentCoursesNumber).toString()
                }

                val leftViewLayout = findViewById<LinearLayout>(R.id.left_view_layout)

                leftViewLayout.addView(view)
                maxCoursesNumber = i
            }
        }


    }

    //创建课程视图
    @SuppressLint("SetTextI18n")
    private fun createCourseView(course: Course) {
        val height = 180
        val getDay = course.day
        if (getDay < 1 || getDay > 7 || course.start > course.end)
            Toast.makeText(this, "星期几没写对,或课程结束时间比开始时间还早~~", Toast.LENGTH_LONG).show()
        else {
            when (getDay) {
                1 -> day = findViewById(R.id.monday)
                2 -> day = findViewById(R.id.tuesday)
                3 -> day = findViewById(R.id.wednesday)
                4 -> day = findViewById(R.id.thursday)
                5 -> day = findViewById(R.id.friday)
                6 -> day = findViewById(R.id.saturday)
                7 -> day = findViewById(R.id.weekday)
            }
            val v = LayoutInflater.from(this).inflate(R.layout.course_card, null) //加载单个课程布局
            v.y = (height * (course.start - 1)).toFloat() //设置开始高度,即第几节课开始
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (course.end - course.start + 1) * height - 8) //设置布局高度,即跨多少节课
            v.layoutParams = params
            val text = v.findViewById<TextView>(R.id.text_view)
            text.text = course.courseName + "\n" + course.teacher + "\n" + course.classRoom //显示课程名
            day!!.addView(v)
            //长按删除课程
            v.setOnLongClickListener { v ->
                v.visibility = View.GONE//先隐藏
                day!!.removeView(v)//再移除课程视图
                val sqLiteDatabase = databaseHelper.writableDatabase
                sqLiteDatabase.execSQL("delete from courses where course_name = ?", arrayOf(course.courseName))
                true
            }
            v.setOnClickListener {
//                v.visibility = View.GONE//先隐藏
//                day!!.removeView(v)//再移除课程视图
                val intent = Intent(this, AddCourseActivity::class.java)
                startActivityForResult(intent, 1)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == 1 && data != null) {
            val course = data.getSerializableExtra("course") as Course
            //创建课程表左边视图(节数)
            createLeftView()
            //创建课程表视图
            createCourseView(course)
            //存储数据到数据库
            saveData(course)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_courses -> {
                val intent = Intent(this, AddCourseActivity::class.java)
                startActivityForResult(intent, 1)
            }

        }
        return true
    }

    fun initinject() {
        DaggerMyCommponent.builder().activityCommpoent(activityCommpoent).mymodule(Mymodule()).build().inject(this)

    }
}