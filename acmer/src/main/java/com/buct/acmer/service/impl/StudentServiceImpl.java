package com.buct.acmer.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buct.acmer.entity.Student;
import com.buct.acmer.entity.StudentDTO;
import com.buct.acmer.mapper.StudentMapper;
import com.buct.acmer.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author BUCT
 * @since 2022-06-14
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    @Resource
    private StudentMapper studentMapper;
    @Override
    public Page<StudentDTO> getStudentAllInfo(int currentPage, int pageSize) {
        Page<StudentDTO> studentDTOPage = new Page<>(currentPage, pageSize);
        return studentMapper.getStudentAllInfo(studentDTOPage);
    }
}
