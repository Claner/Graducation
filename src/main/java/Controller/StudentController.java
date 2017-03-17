package Controller;

import Dao.*;
import Entity.*;
import Util.Constant;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Clanner on 2017/3/7.
 */
@RestController
@RequestMapping("Student")
@CrossOrigin
public class StudentController {

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private AcademyDao academyDao;

    @Autowired
    private GradeDao gradeDao;

    @Autowired
    private ProfessionalDao professionalDao;

    @Autowired
    private ProfessionalCourseDao professionalCourseDao;

    @Autowired
    private StudentProfessionalCourseDao studentProfessionalCourseDao;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private Response.Builder builder;

    /**
     * 获取学生信息
     */
    @RequestMapping(value = "/getStudentInfo", method = RequestMethod.POST)
    public Response getStudentInfo(HttpSession session) {
        StudentEntity studentEntity = studentDao.selectStudent((Integer) session.getAttribute("isLogin"));
        if (studentEntity != null) {
            Student student = new Student();
            student.setStuId(studentEntity.getStuId());
            student.setAcademyId(studentEntity.getAcademyId());
            student.setProfessionalId(studentEntity.getProfessionalId());
            student.setGradeId(studentEntity.getGradeId());
            student.setName(studentEntity.getName());
            student.setSex(studentEntity.getSex());
            student.setPhone(studentEntity.getPhone());
            student.setAddress(studentEntity.getAddress());
            student.setAcademyName(academyDao.getAcademyName(studentEntity.getAcademyId()));
            student.setProfessionalName(professionalDao.getProfessionalName(studentEntity.getProfessionalId()));
            student.setGrade(gradeDao.getGrade(studentEntity.getGradeId()));
            return builder.setCode(20000).setMessage("查询成功").setData(student).build();
        } else {
            return builder.setCode(40000).setMessage("学生不存在").build();
        }
    }

    /**
     * 修改学生信息
     */
    @RequestMapping(value = "/modifyStudentInfo", method = RequestMethod.POST)
    public Response modifyStudentInfo(HttpSession session,
                                      @RequestParam("name") String name,
                                      @RequestParam("sex") String sex,
                                      @RequestParam("phone") String phone,
                                      @RequestParam("address") String address) {
        switch (studentDao.updateStudent((Integer) session.getAttribute("isLogin"), name, sex, phone, address)) {
            case Constant.UPDATE_SUCCESS:
                return builder.setCode(20000).setMessage("修改学生信息成功").build();
            case Constant.UPDATE_FAILURE:
                return builder.setCode(40000).setMessage("修改学生信息失败").build();
            default:
                return builder.setCode(40000).setMessage("未知错误").build();
        }
    }

    /**
     * 获取学生课表
     */
    @RequestMapping(value = "/getStudentCourse", method = RequestMethod.POST)
    public Response getStudentCourse(HttpSession session) {
        StudentEntity studentEntity = studentDao.selectStudent((Integer) session.getAttribute("isLogin"));
        if (studentEntity != null) {
            List<ProfessionalCourseEntity> list = professionalCourseDao.getTargetCourse(studentEntity.getGradeId(), studentEntity.getProfessionalId());
            if (list != null) {
                List<Integer> integerList = studentProfessionalCourseDao.getStuProfessionalCourse(studentEntity.getStuId());
                if (integerList != null && integerList.size() > 0) {
                    for (int i : integerList) {
                        ProfessionalCourseEntity professionalCourseEntity =
                                professionalCourseDao.getProfessionalCourseById(i, studentEntity.getGradeId());
                        if (professionalCourseEntity != null) {
                            list.add(professionalCourseEntity);
                        }
                    }
                }
                List<ProfessionalCourse> professionalCourseList = changeToProfessionalCourse(list);
                return builder.setCode(20000).setMessage("获取学生课表成功").setDataList(professionalCourseList).build();
            } else {
                return builder.setCode(40000).setMessage("暂无课表").build();
            }
        } else {
            return builder.setCode(40000).setMessage("获取学生信息失败").build();
        }
    }

    /**
     * 通过年级id获取学生课表
     */
    @RequestMapping(value = "/getStudentCourseByGradeId", method = RequestMethod.POST)
    public Response getStudentCourseByGradeId(HttpSession session, @RequestParam("grade_id") int grade_id) {
        StudentEntity studentEntity = studentDao.selectStudent((Integer) session.getAttribute("isLogin"));
        if (studentEntity != null) {
            List<ProfessionalCourseEntity> list = professionalCourseDao.getTargetCourse(grade_id, studentEntity.getProfessionalId());
            if (list != null) {
                List<Integer> integerList = studentProfessionalCourseDao.getStuProfessionalCourse(studentEntity.getStuId());
                if (integerList != null && integerList.size() > 0) {
                    for (int i : integerList) {
                        ProfessionalCourseEntity professionalCourseEntity =
                                professionalCourseDao.getProfessionalCourseById(i, grade_id);
                        if (professionalCourseEntity != null) {
                            list.add(professionalCourseEntity);
                        }
                    }
                }
                List<ProfessionalCourse> professionalCourseList = changeToProfessionalCourse(list);
                return builder.setCode(20000).setMessage("获取学生课表成功").setDataList(professionalCourseList).build();
            } else {
                return builder.setCode(40000).setMessage("暂无课表").build();
            }
        } else {
            return builder.setCode(40000).setMessage("获取学生信息失败").build();
        }
    }

    /**
     * 学生获取选课课表
     */
    @RequestMapping(value = "/getSelectableCourse", method = RequestMethod.POST)
    public Response getSelectableCourse(HttpSession session) {
        StudentEntity studentEntity = studentDao.selectStudent((Integer) session.getAttribute("isLogin"));
        if (studentEntity != null) {
            List<ProfessionalCourseEntity> list = professionalCourseDao.getSelectableCourse(studentEntity.getGradeId()
                    , getStudentTimeList(studentEntity.getStuId(), studentEntity.getGradeId(), studentEntity.getProfessionalId()));
            if (list != null && list.size() > 0) {
                List<ProfessionalCourse> professionalCourseList = changeToProfessionalCourse(list);
                return builder.setCode(20000).setMessage("获取课程成功").setDataList(professionalCourseList).build();
            } else {
                return builder.setCode(40000).setMessage("暂无可选的课程").build();
            }
        } else {
            return builder.setCode(40000).setMessage("获取学生信息失败").build();
        }
    }


    /**
     * 获取学生所有上课时间
     */
    private List<String> getStudentTimeList(int stu_id, int grade_id, int professional_id) {
        List<String> list = professionalCourseDao.getAllTime(grade_id, professional_id);
        List<StuProfessionalCourseEntity> stuProfessionalCourseEntityList = studentProfessionalCourseDao.getAllStuProfessionalCourse(stu_id);
        if (list != null && list.size() > 0) {
            if (stuProfessionalCourseEntityList != null && stuProfessionalCourseEntityList.size() > 0) {
                for (StuProfessionalCourseEntity stuProfessionalCourseEntity : stuProfessionalCourseEntityList) {
                    String s = professionalCourseDao.getTimeById(stuProfessionalCourseEntity.getArrangeId(), grade_id);
                    if (s != null) {
                        list.add(s);
                    }
                }

                System.out.println("选修课表不为空");
                for (String s : list) {
                    System.out.println("时间为" + s);
                }
                return list;
            } else {
                System.out.println("选修课表为空");
                for (String s : list) {
                    System.out.println("时间为" + s);
                }
                return list;
            }
        } else {
            return null;
        }
    }

    /**
     * 学生选课
     */
    @RequestMapping(value = "/selectCourse", method = RequestMethod.POST)
    public Response selectCourse(@RequestParam("arrange_id") int arrange_id, HttpSession session) {
        if (professionalCourseDao.isExist(arrange_id)) {
            if (!professionalCourseDao.isPublic(arrange_id))
                return builder.setCode(40000).setMessage("该课程不是公选课").build();
            switch (studentProfessionalCourseDao.selectCourse((Integer) session.getAttribute("isLogin"), arrange_id)) {
                case Constant.SAVE_SUCCESS:
                    return builder.setCode(20000).setMessage("选课成功").build();
                case Constant.SAVE_FAILURE:
                    return builder.setCode(40000).setMessage("已选该课程").build();
                default:
                    return builder.setCode(40000).setMessage("未知错误").build();
            }
        } else {
            return builder.setCode(40000).setMessage("该课程不存在").build();
        }
    }

    private List<ProfessionalCourse> changeToProfessionalCourse(List<ProfessionalCourseEntity> list) {
        List<ProfessionalCourse> professionalCourseList = new ArrayList<ProfessionalCourse>();
        for (ProfessionalCourseEntity p : list) {
            ProfessionalCourse professionalCourse = new ProfessionalCourse();
            professionalCourse.setId(p.getId());
            professionalCourse.setGradeId(p.getGradeId());
            professionalCourse.setProfessionalId(p.getProfessionalId());
            professionalCourse.setCourseId(p.getCourseId());
            professionalCourse.setTime(p.getTime());
            professionalCourse.setGrade(gradeDao.getGrade(p.getGradeId()));
            professionalCourse.setProfessionalName(professionalDao.getProfessionalName(p.getProfessionalId()));
            professionalCourse.setCourseName(courseDao.getCourseName(p.getCourseId()));
            professionalCourseList.add(professionalCourse);
        }
        return professionalCourseList;
    }

    /**
     * 学生退课
     */
    @RequestMapping(value = "/deleteCourse", method = RequestMethod.POST)
    public Response deleteCourse(@RequestParam("arrange_id") int arrange_id, HttpSession session) {
        switch (studentProfessionalCourseDao.deleteCourse((Integer)
                session.getAttribute("isLogin"), arrange_id)) {
            case Constant.DELETE_SUCCESS:
                return builder.setCode(20000).setMessage("退课成功").build();
            case Constant.DELETE_FAILURE:
                return builder.setCode(40000).setMessage("退课失败").build();
            case Constant.NOT_EXIST:
                return builder.setCode(40000).setMessage("已退选该课程").build();
            default:
                return builder.setCode(40000).setMessage("未知错误").build();
        }
    }

    /**
     * 查成绩
     */
    @RequestMapping(value = "/getStudentGrade", method = RequestMethod.POST)
    public Response getStudentGrade(@RequestParam("account") String account,
                                    @RequestParam("password") String password,
                                    @RequestParam("grade_id") int grade_id) {

        String url = "http://wyugrade.bensonwu.cn/Home/Student/getStudentGrade";
        try {
            Document document = Jsoup.connect(url)
                    .timeout(36000)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .header("Accept-Encoding", "gzip, deflate, sdch")
                    .header("Accept-Language", "zh-CN,zh;q=0.8")
                    .header("Connection", "keep-alive")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36")
                    .data("student_num", account)
                    .data("password", password)
                    .ignoreContentType(true)
                    .post();

            String json = document.body().text();
            JSONObject jsonObject = JSONObject.parseObject(json);
            int code = jsonObject.getInteger("code");
            if (code == 20000) {
                List<GradeStudentScore> allList = new ArrayList<GradeStudentScore>();
                JSONArray allJsonArray = jsonObject.getJSONArray("response");
                for (int i = 0; i < allJsonArray.size(); i++) {
                    List<StudentScore> list = new ArrayList<StudentScore>();
                    JSONArray jsonArray = allJsonArray.getJSONArray(i);
                    for (int j = 0; j < jsonArray.size(); j++) {
                        StudentScore studentScore = new StudentScore();
                        JSONObject object = jsonArray.getJSONObject(j);
                        studentScore.setNumber(object.getString("number"));
                        studentScore.setName(object.getString("name"));
                        studentScore.setType(object.getString("type"));
                        studentScore.setCredit(object.getString("credit"));
                        studentScore.setScore(object.getString("grade"));
                        studentScore.setTip(object.getString("tip"));
                        list.add(studentScore);
                    }
                    allList.add(new GradeStudentScore(i + 1, list));
                }
                if (grade_id>allList.size())
                    return builder.setCode(40000).setMessage("暂无没有该年级的成绩").build();
                switch (grade_id) {
                    case 0:
                        return builder.setCode(20000).setMessage("获取学生成绩成功").setDataList(allList).build();
                    default:
                        return builder.setCode(20000).setMessage("获取学生成绩成功").setData(allList.get(grade_id - 1)).build();
                }
            } else {
                return builder.setCode(40000).setMessage("用户名或密码错误").build();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return builder.setCode(40000).setMessage("子系统崩溃ing").build();
        }
    }
}
