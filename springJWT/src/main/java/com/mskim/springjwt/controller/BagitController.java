package com.mskim.springjwt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mskim.springjwt.dto.BagitVO;
import gov.loc.repository.bagit.creator.BagCreator;
import gov.loc.repository.bagit.domain.Metadata;
import gov.loc.repository.bagit.hash.StandardSupportedAlgorithms;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@Slf4j
public class BagitController {

    private StandardSupportedAlgorithms algorithm = StandardSupportedAlgorithms.SHA512;

    @Resource
    private ObjectMapper objectMapper;

    @PostMapping("/bagit/create")
    public String bagitCreate () {

        BagitVO bagitVO = new BagitVO();
        bagitVO.setId(UUID.randomUUID().toString());
        bagitVO.setName("백잇 테스트");
        bagitVO.setAttribute1("111");
        bagitVO.setAttribute2("222");
        bagitVO.setAttribute3("333");

        Map<String, Object> bagitData = new HashMap<>();
        bagitData.put("1","1");
        bagitData.put("2","2");
        bagitData.put("3","3");
        bagitData.put("4","4");

        sipPackagingAdmnsData(bagitData);

        return "백잇 생성 성공";
    }

    public void sipPackagingAdmnsData(Map<String, Object> bagitData) {

        String basePath = "C:\\Users\\김민식\\Desktop\\work\\msaWebService\\data\\bag-it";

        try {
            Files.delete(Paths.get(basePath));
            Path bagitPath = Files.createDirectories(Paths.get(basePath));

            objectMapper.writerWithDefaultPrettyPrinter().writeValue(bagitPath.resolve("meta-info.json").toFile(), bagitData);

            try {
                // BagIt 생성
                generateBagit(bagitPath);

            } catch (NullPointerException e) {
                log.info(e.getMessage());
            } catch (Exception e) {
                log.info(e.getMessage());
            }

        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

    public void generateBagit(Path bagitPath) {
        try {
            Metadata bagitBaseInfo = new Metadata();
            bagitBaseInfo.add("Source-Organization", "소스-기관");
            bagitBaseInfo.add("Organization-Address", "기관-주소");
            bagitBaseInfo.add("Contact-Name", "생산자");
            bagitBaseInfo.add("Contact-Phone", "연락처");
            bagitBaseInfo.add("Contact-Email", "이메일");

            BagCreator.bagInPlace(bagitPath, Arrays.asList(algorithm), false, bagitBaseInfo);
        } catch (NullPointerException e) {
            log.info(e.getMessage());
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }
}
