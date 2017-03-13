package Controller;

import Dao.*;
import Entity.*;
import Util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Clanner on 2017/3/6.
 */
@RestController
@RequestMapping("Admin")
public class AdminController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private AcademyDao academyDao;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private ProfessionalDao professionalDao;

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private ProfessionalCourseDao professionalCourseDao;

    @Autowired
    private GradeDao gradeDao;

    @Autowired
    private Response.Builder builder;

    /**
     * 录入学生信息
     */
    @RequestMapping(value = "/saveStudent", method = RequestMethod.POST)
    public Response saveStudent(@RequestParam("account") String account,
                                @RequestParam("academy_id") int academy_id,
                                @RequestParam("professional_id") int professional_id,
                                @RequestParam("grade") int grade,
                                @RequestParam("name") String name,
                                @RequestParam("sex") String sex,
                                @RequestParam(value = "phone", required = false) String phone,
                                @RequestParam(value = "address", required = false) String address) {
        if (!academyDao.isAcademyExist(academy_id))
            return builder.setCode(40000).setMessage("学院不存在").build();
        if (!professionalDao.isProfessionalExist(professional_id))
            return builder.setCode(40000).setMessage("专业不存在").build();
        if (userDao.isUserExist(account)) return builder.setCode(40000).setMessage("该用户已存在").build();
        int user_id = userDao.saveUser(account);
        if (user_id != 0) {
            if (studentDao.saveStudent(user_id, academy_id, professional_id, grade, name, sex, phone, address)) {
                return builder.setCode(20000).setMessage("录入学生信息成功").build();
            } else {
                return builder.setCode(40000).setMessage("录入学生成功,但保存学生信息失败").build();
            }
        } else {
            return builder.setCode(40000).setMessage("录入学生信息失败").build();
        }
    }

    /**
     * 删除学生信息
     */
    @RequestMapping(value = "/deleteStudent", method = RequestMethod.POST)
    public Response deleteStudent(@RequestParam("stu_id") int stu_id) {
        if (!studentDao.isStudentExist(stu_id)) return builder.setCode(40000).setMessage("该学生已被删除").build();
        if (adminDao.deleteStudent(stu_id)) {
            return builder.setCode(20000).setMessage("删除学生成功").build();
        } else {
            return builder.setCode(40000).setMessage("删除学生成功").build();
        }
    }

    /**
     * 修改学生学院，专业，和年级
     */
    @RequestMapping(value = "/modifyStudent", method = RequestMethod.POST)
    public Response modifyStudent(@RequestParam("stu_id") int stu_id,
                                  @RequestParam("academy_id") int academy_id,
                                  @RequestParam("professional_id") int professional_id,
                                  @RequestParam("grade") int grade) {
        if (!academyDao.isAcademyExist(academy_id))
            return builder.setCode(40000).setMessage("学院不存在").build();
        if (!professionalDao.isProfessionalExist(professional_id))
            return builder.setCode(40000).setMessage("专业不存在").build();
        switch (studentDao.updateStudentAcademyOrProfessionalOrGrade(stu_id, academy_id, professional_id, grade)) {
            case Constant.UPDATE_SUCCESS:
                return builder.setCode(20000).setMessage("修改学生信息成功").build();
            case Constant.UPDATE_FAILURE:
                return builder.setCode(40000).setMessage("修改学生信息失败").build();
            default:
                return builder.setCode(40000).setMessage("未知错误").build();
        }
    }

    /**
     * 录入学院
     */
    @RequestMapping(value = "/saveAcademy", method = RequestMethod.POST)
    public Response saveAcademy(@RequestParam("academy_name") String academyName) {
        switch (academyDao.saveAcademy(academyName)) {
            case Constant.SAVE_SUCCESS:
                return builder.setCode(20000).setMessage("录入学院成功").build();
            case Constant.SAVE_FAILURE:
                return builder.setCode(40000).setMessage("录入学院失败").build();
            default:
                return builder.setCode(40000).setMessage("已录入该学院").build();
        }
    }

    /**
     * 删除学院信息
     */
    @RequestMapping(value = "/deleteAcademy", method = RequestMethod.POST)
    public Response deleteAcademy(@RequestParam("id") int id) {
        if (!professionalDao.isHasProfessional(id)) {
            switch (academyDao.deleteAcademyWhitoutProfessional(id)) {
                case Constant.DELETE_SUCCESS:
                    return builder.setCode(20000).setMessage("删除学院信息成功").build();
                case Constant.DELETE_FAILURE:
                    return builder.setCode(40000).setMessage("该学院信息已删除").build();
                default:
                    return builder.setCode(40000).setMessage("未知错误").build();
            }
        } else {
            switch (academyDao.deleteAcademy(id)) {
                case Constant.DELETE_SUCCESS:
                    return builder.setCode(20000).setMessage("删除学院信息成功").build();
                case Constant.DELETE_FAILURE:
                    return builder.setCode(40000).setMessage("该学院信息已删除").build();
                default:
                    return builder.setCode(40000).setMessage("未知错误").build();
            }
        }
    }

    /**
     * 修改学院信息
     */
    @RequestMapping(value = "/modifyAcademyInfo", method = RequestMethod.POST)
    public Response modifyAcademyInfo(@RequestParam("academy_id") int academy_id,
                                      @RequestParam("academy_name") String academy_name) {
        switch (academyDao.updateAcademyInfo(academy_id, academy_name)) {
            case Constant.UPDATE_SUCCESS:
                return builder.setCode(20000).setMessage("修改学院信息成功").build();
            case Constant.UPDATE_FAILURE:
                return builder.setCode(40000).setMessage("修改学院信息失败").build();
            case Constant.HAS_EXIST:
                return builder.setCode(40000).setMessage("学院已存在").build();
            default:
                return builder.setCode(40000).setMessage("学院不存在").build();
        }
    }

    /**
     * 录入专业
     */
    @RequestMapping(value = "/saveProfessional", method = RequestMethod.POST)
    public Response saveProfessional(@RequestParam("academy_id") int academy_id,
                                     @RequestParam("professional_name") String professional_name,
                                     @RequestParam("year") String year) {
        if (!academyDao.isAcademyExist(academy_id)) return builder.setCode(40000).setMessage("该学院不存在").build();
        switch (professionalDao.saveProfessional(academy_id, professional_name, year)) {
            case Constant.SAVE_SUCCESS:
                return builder.setCode(20000).setMessage("录入专业成功").build();
            case Constant.SAVE_FAILURE:
                return builder.setCode(20000).setMessage("录入专业失败").build();
            default:
                return builder.setCode(40000).setMessage("该专业已存在").build();
        }
    }

    /**
     * 删除专业
     */
    @RequestMapping(value = "/deleteProfessional", method = RequestMethod.POST)
    public Response deleteProfessional(@RequestParam("id") int id) {
        switch (professionalDao.deleteProfessional(id)) {
            case Constant.DELETE_SUCCESS:
                return builder.setCode(20000).setMessage("删除专业成功").build();
            case Constant.DELETE_FAILURE:
                return builder.setCode(40000).setMessage("该专业已删除").build();
            default:
                return builder.setCode(40000).setMessage("未知错误").build();
        }
    }

    /**
     * 修改专业信息
     */
    @RequestMapping(value = "/modifyProfessionalInfo", method = RequestMethod.POST)
    public Response modifyProfessionalInfo(@RequestParam("id") int id,
                                           @RequestParam("academy_id") int academy_id,
                                           @RequestParam("pro_name") String pro_name,
                                           @RequestParam("year") String year) {
        switch (professionalDao.updateProfessionalInfo(id, academy_id, pro_name, year)) {
            case Constant.UPDATE_SUCCESS:
                return builder.setCode(20000).setMessage("修改专业信息成功").build();
            case Constant.UPDATE_FAILURE:
                return builder.setCode(40000).setMessage("修改专业信息失败").build();
            case Constant.HAS_EXIST:
                return builder.setCode(40000).setMessage("专业已存在").build();
            default:
                return builder.setCode(40000).setMessage("专业信息不存在").build();
        }
    }

    /**
     * 录入课程信息
     */
    @RequestMapping(value = "/saveCourse", method = RequestMethod.POST)
    public Response saveCourse(@RequestParam("course_name") String course_name,
                               @RequestParam("course_credit") int course_credit,
                               @RequestParam("course_hour") int hour) {
        switch (courseDao.saveCourse(course_name, course_credit, hour)) {
            case Constant.SAVE_SUCCESS:
                return builder.setCode(20000).setMessage("录入课程成功").build();
            case Constant.SAVE_FAILURE:
                return builder.setCode(40000).setMessage("录入课程失败").build();
            default:
                return builder.setCode(40000).setMessage("该课程已存在").build();
        }
    }

    /**
     * 删除课程信息
     */
    @RequestMapping(value = "/deleteCourse", method = RequestMethod.POST)
    public Response deleteCourse(@RequestParam("id") int id) {
        switch (courseDao.deleteCourse(id)) {
            case Constant.DELETE_SUCCESS:
                return builder.setCode(20000).setMessage("删除课程信息成功").build();
            case Constant.DELETE_FAILURE:
                return builder.setCode(40000).setMessage("删除课程信息失败").build();
            default:
                return builder.setCode(40000).setMessage("未知错误").build();
        }
    }

    /**
     * 修改课程信息
     */
    @RequestMapping(value = "/modifyCourseInfo", method = RequestMethod.POST)
    public Response modifyCourseInfo(@RequestParam("id") int id,
                                     @RequestParam("course_name") String course_name,
                                     @RequestParam("course_credit") int course_credit,
                                     @RequestParam("course_hour") int course_hour) {
        switch (courseDao.updateCourseInfo(id, course_name, course_credit, course_hour)) {
            case Constant.UPDATE_SUCCESS:
                return builder.setCode(20000).setMessage("修改课程信息成功").build();
            case Constant.UPDATE_FAILURE:
                return builder.setCode(40000).setMessage("修改课程信息失败").build();
            case Constant.HAS_EXIST:
                return builder.setCode(40000).setMessage("课程已存在").build();
            default:
                return builder.setCode(40000).setMessage("课程不存在").build();
        }
    }

    /**
     * 查询学生信息
     */
    @RequestMapping(value = "/getStudentInfo", method = RequestMethod.POST)
    public Response getStudentInfo(@RequestParam("stu_id") int stu_id) {
        StudentEntity studentEntity = studentDao.selectStudent(stu_id);
        if (studentEntity != null) {
            return builder.setCode(20000).setMessage("查询成功").setData(studentEntity).build();
        } else {
            return builder.setCode(40000).setMessage("学生不存在").build();
        }
    }

    /**
     * 获取管理员信息
     */
    @RequestMapping(value = "/getAdminInfo", method = RequestMethod.POST)
    public Response getAdminInfo(HttpSession session) {
        AdminEntity adminEntity = adminDao.getAdminInfo((Integer) session.getAttribute("isLogin"));
        if (adminEntity != null) {
            return builder.setCode(20000).setMessage("获取信息成功").setData(adminEntity).build();
        } else {
            return builder.setCode(40000).setMessage("获取管理员信息失败").build();
        }
    }

    /**
     * 修改管理员信息
     */
    @RequestMapping(value = "/modifyAdminInfo", method = RequestMethod.POST)
    public Response modifyAdminInfo(HttpSession session,
                                    @RequestParam("name") String name,
                                    @RequestParam("sex") String sex,
                                    @RequestParam("phone") String phone,
                                    @RequestParam("address") String address) {
        switch (adminDao.updateAdminInfo((Integer) session.getAttribute("isLogin"), name, sex, phone, address)) {
            case Constant.UPDATE_SUCCESS:
                return builder.setCode(20000).setMessage("修改管理员信息成功").build();
            case Constant.UPDATE_FAILURE:
                return builder.setCode(40000).setMessage("修改管理员信息失败").build();
            default:
                return builder.setCode(40000).setMessage("未知错误").build();
        }
    }

    /**
     * 获取所有学生信息
     */
    @RequestMapping(value = "/getAllStudentInfo", method = RequestMethod.POST)
    public Response getAllStudentInfo(@RequestParam("pageNo") int pageNo,
                                      @RequestParam("pageSize") int pageSize) {
        long size = studentDao.getCount();
        List<StudentEntity> list = studentDao.getAllStudentInfo(pageNo, pageSize);
        List<Student> studentList = new ArrayList<Student>();
        if (list != null && list.size() > 0) {
            for (StudentEntity studentEntity : list) {
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
                studentList.add(student);
            }
            return builder.setCode(20000).setMessage("获取数据成功").setData(size).setDataList(studentList).build();
        } else {
            return builder.setCode(40000).setMessage("暂无数据").build();
        }
    }

    /**
     * 获取所有学院信息
     */
    @RequestMapping(value = "/getAllAcademy", method = RequestMethod.POST)
    public Response getAllAcademy(@RequestParam(value = "pageNo", required = false) int pageNo,
                                  @RequestParam(value = "pageSize", required = false) int pageSize) {
        long size = academyDao.getCount();
        List<AcademyEntity> list = academyDao.getAllAcademy(pageNo, pageSize);
        if (list != null && list.size() != 0) {
            return builder.setCode(20000).setMessage("查询成功").setData(size).setDataList(list).build();
        } else {
            return builder.setCode(40000).setMessage("没有数据了").build();
        }
    }

    /**
     * 获取所有专业信息
     */
    @RequestMapping(value = "/getAllProfessional", method = RequestMethod.POST)
    public Response getAllProfessional(@RequestParam(value = "pageNo", required = false) int pageNo,
                                       @RequestParam(value = "pageSize", required = false) int pageSize) {
        long size = professionalDao.getCount();
        List<ProfessionalEntity> list = professionalDao.getAllProfessional(pageNo, pageSize);
        if (list != null && list.size() > 0) {
            return builder.setCode(20000).setMessage("获取数据成功").setData(size).setDataList(list).build();
        } else {
            return builder.setCode(40000).setMessage("没有数据").build();
        }
    }

    /**
     * 获取学院对应专业
     */
    @RequestMapping(value = "/getTargetProfessional", method = RequestMethod.POST)
    public Response getTargetProfessional(@RequestParam("academy_id") int academy_id) {
        List<ProfessionalEntity> list = professionalDao.getTargetProfessional(academy_id);
        if (list != null && list.size() > 0) {
            return builder.setCode(20000).setMessage("获取专业成功").setDataList(list).build();
        } else {
            return builder.setCode(40000).setMessage("没有数据").build();
        }
    }

    /**
     * 获取所有课程数据
     */
    @RequestMapping(value = "/getAllCourse", method = RequestMethod.POST)
    public Response getAllCourse(@RequestParam(value = "pageNo", required = false) int pageNo,
                                 @RequestParam(value = "pageSize", required = false) int pageSize) {
        long size = courseDao.getCount();
        List<CourseEntity> list = courseDao.getAllCourse(pageNo, pageSize);
        if (list != null && list.size() > 0) {
            return builder.setCode(20000).setMessage("获取课程成功").setData(size).setDataList(list).build();
        } else {
            return builder.setCode(40000).setMessage("没有数据").build();
        }
    }

    /**
     * 管理员录入排课信息
     */
    @RequestMapping(value = "/saveProfessionalCourse", method = RequestMethod.POST)
    public Response saveProfessionalCourse(@RequestParam("grade_id") int grade_id,
                                           @RequestParam("professional_id") int professional_id,
                                           @RequestParam("course_id") int course_id,
                                           @RequestParam("time") String time) {
        if (grade_id > 8 || grade_id < 1)
            return builder.setCode(40000).setMessage("年级超出范围").build();
        if (!courseDao.isCourseExist(course_id))
            return builder.setCode(40000).setMessage("该课程不存在").build();
        switch (professionalCourseDao.saveProfessionalCourse(grade_id, professional_id, course_id, time)) {
            case Constant.SAVE_SUCCESS:
                return builder.setCode(20000).setMessage("排课成功").build();
            case Constant.PARAMS_NOT_MATCH:
                return builder.setCode(40000).setMessage("参数不合法").build();
            case Constant.COURSE_CONFLICT:
                return builder.setCode(40000).setMessage("课程时间冲突").build();
            case Constant.HAS_EXIST:
                return builder.setCode(40000).setMessage("已安排过该课程").build();
            default:
                return builder.setCode(40000).setMessage("未知错误").build();
        }
    }

    /**
     * 获取所有排课信息
     */
    @RequestMapping(value = "/getAllProfessionalCourse", method = RequestMethod.POST)
    public Response getAllProfessionalCourse(@RequestParam(value = "pageNo", required = false) int pageNo,
                                             @RequestParam(value = "pageSize", required = false) int pageSize) {
        long size = professionalCourseDao.getCount();
        List<ProfessionalCourseEntity> list = professionalCourseDao.getAllProfessionalCourse(pageNo, pageSize);
        if (list != null && list.size() > 0) {
            List<ProfessionalCourse> pList = changeToProfessionalCourse(list);
            return builder.setCode(20000).setMessage("获取数据成功").setData(size).setDataList(pList).build();
        } else {
            return builder.setCode(40000).setMessage("没有数据").build();
        }
    }

    /**
     * 修改排课信息
     */
    @RequestMapping(value = "/modifyProfessionalCourse", method = RequestMethod.POST)
    public Response modifyProfessionalCourse(@RequestParam("id") int id,
                                             @RequestParam("grade_id") int grade_id,
                                             @RequestParam("professional_id") int professional_id,
                                             @RequestParam("course_id") int course_id,
                                             @RequestParam("time") String time) {
        switch (professionalCourseDao.updateProfessionalCourse(id, grade_id, professional_id, course_id, time)) {
            case Constant.UPDATE_SUCCESS:
                return builder.setCode(20000).setMessage("修改排课信息成功").build();
            case Constant.UPDATE_FAILURE:
                return builder.setCode(40000).setMessage("修改排课信息失败").build();
            case Constant.PARAMS_NOT_MATCH:
                return builder.setCode(40000).setMessage("参数不正确").build();
            case Constant.COURSE_CONFLICT:
                return builder.setCode(40000).setMessage("课程时间冲突").build();
            case Constant.HAS_EXIST:
                return builder.setCode(40000).setMessage("已安排过该课程").build();
            case Constant.NOT_EXIST:
                return builder.setCode(40000).setMessage("排课信息不存在").build();
            default:
                return builder.setCode(40000).setMessage("未知错误").build();
        }
    }

    /**
     * 删除排课信息
     */
    @RequestMapping(value = "/deleteProfessionalCourse", method = RequestMethod.POST)
    public Response deleteProfessionalCourse(@RequestParam("id") int id) {
        switch (professionalCourseDao.deleteProfessionalCourse(id)) {
            case Constant.DELETE_SUCCESS:
                return builder.setCode(20000).setMessage("删除排课信息成功").build();
            case Constant.DELETE_FAILURE:
                return builder.setCode(40000).setMessage("该排课信息已删除").build();
            default:
                return builder.setCode(40000).setMessage("未知错误").build();
        }
    }

    /**
     * 管理员批量录入排课信息
     */
    @RequestMapping(value = "/saveMoreProfessionalCourse", method = RequestMethod.POST)
    public Response saveMoreProfessionalCourse(@RequestParam("grade_id") int grade_id,
                                               @RequestParam("professional_id") int professional_id,
                                               @RequestParam("course_id") int[] courses_id,
                                               @RequestParam("times") String[] times) {
        switch (professionalCourseDao.saveMoreProfessionalCourse(grade_id, professional_id, courses_id, times)) {
            case Constant.SAVE_SUCCESS:
                return builder.setCode(20000).setMessage("排课成功").build();
            case Constant.PARAMS_NOT_MATCH:
                return builder.setCode(40000).setMessage("参数不合法").build();
            case Constant.COURSE_CONFLICT:
                return builder.setCode(40000).setMessage("课程时间冲突").build();
            case Constant.HAS_EXIST:
                return builder.setCode(40000).setMessage("已安排过该课程").build();
            default:
                return builder.setCode(40000).setMessage("未知错误").build();
        }
    }

    /**
     * 获取指定年级和专业的排课信息
     */
    @RequestMapping(value = "/getTargetProfessionalCourse", method = RequestMethod.POST)
    public Response getTargetProfessionalCourse(@RequestParam("grade_id") int grade_id,
                                                @RequestParam("professional_id") int professional_id) {
        List<ProfessionalCourseEntity> list = professionalCourseDao.getTargetProfessionalCourse(grade_id, professional_id);
        if (list != null && list.size() > 0) {
            List<ProfessionalCourse> professionalCourseList = changeToProfessionalCourse(list);
            return builder.setCode(20000).setMessage("获取成功").setDataList(professionalCourseList).build();
        } else {
            return builder.setCode(40000).setMessage("暂无课程信息").build();
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
}
