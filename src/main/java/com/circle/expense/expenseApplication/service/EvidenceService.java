package com.circle.expense.expenseApplication.service;

import com.circle.expense.expenseApplication.entity.Evidence;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 凭证表 服务类
 * </p>
 *
 * @author yuan.liu
 * @since 2019-05-11
 */
public interface EvidenceService extends IService<Evidence> {

    public Evidence uploadEvidence(MultipartFile uploadFile);

}
