package com.buct.acmer.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buct.acmer.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.buct.acmer.entity.StudentDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author BUCT
 * @since 2022-06-14
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {
    @Select("SELECT student.stu_no, student.stu_name, student.stu_class, student.stu_cf_id, student.stu_ac_id, cf.cf_new_rating, cf.cf_sum_contest, sub_query.cf_user_id, sub_query.cf_ac_number_sum, sub_query.cf_sc_number_sum " +
            "FROM student student " +
            "JOIN codeforces cf ON student.stu_cf_id = cf.cf_id " +
            "JOIN (SELECT cfr.cf_user_id, SUM(cfr.cf_ac_number) AS cf_ac_number_sum, SUM(cfr.cf_sc_number) AS cf_sc_number_sum " +
            "      FROM cfrating cfr " +
            "      GROUP BY cfr.cf_user_id) AS sub_query ON student.stu_cf_id = sub_query.cf_user_id")
    Page<StudentDTO> getStudentAllInfo(Page<StudentDTO> page);

}


