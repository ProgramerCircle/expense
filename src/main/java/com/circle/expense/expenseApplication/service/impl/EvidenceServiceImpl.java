package com.circle.expense.expenseApplication.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.circle.core.exception.BizException;
import com.circle.expense.expenseApplication.entity.Evidence;
import com.circle.expense.expenseApplication.mapper.EvidenceMapper;
import com.circle.expense.expenseApplication.service.EvidenceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;

/**
 * <p>
 * 凭证表 服务实现类
 * </p>
 *
 * @author yuan.liu
 * @since 2019-05-11
 */
@Service
public class EvidenceServiceImpl extends ServiceImpl<EvidenceMapper, Evidence> implements EvidenceService {

    @Value("${expense.image.path}")
    private String imageSavePath;

    @Value("${expense.image.baseUrl}")
    private String baseUrl;

    @Override
    @Transactional
    public Evidence uploadEvidence(MultipartFile uploadFile) {
        long id = IdWorker.getId();
        Evidence evidence = new Evidence();
        String oldName = uploadFile.getOriginalFilename();
        String newName = IdWorker.get32UUID() + oldName.substring(oldName.lastIndexOf("."));
        evidence.setId(id);
        evidence.setPath(baseUrl+"/"+newName);
        baseMapper.insert(evidence);
        try {
            uploadFile.transferTo(new File(imageSavePath+"/"+newName ));
        }catch (Exception e){
            e.printStackTrace();
            throw new BizException("图片上传失败！");
        }
        return evidence;
    }
}
