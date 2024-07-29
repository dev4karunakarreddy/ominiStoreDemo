package com.Demo.service;
import com.Demo.dto.EmployeeDataDTO;
import com.Demo.dto.OmniCarSystemAccess.AvanserOmniRewardsProgramsDTO;
import com.Demo.dto.OmniCarSystemAccess.RetailerDashBoardOneVoiceDTO;
import com.Demo.dto.OmniCarSystemAccess.TrafficReportOminiStoreDTO;
import com.Demo.dto.OmniCarSystemAccess.WorkPlaceSesimiDTO;
import com.Demo.dto.RetailerAddressDTO;
import com.Demo.repository.EmployeeDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class PdfService {

    @Autowired
    private EmployeeDataRepository employeeDataRepository;

    public void extractAndSaveData(MultipartFile file) throws IOException {
        PDDocument document = PDDocument.load(file.getInputStream());
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String text = pdfStripper.getText(document);
        document.close();
        EmployeeDataDTO employeeDataDTO = createEmployeeData(text);
        log.info("Employee Data: {}", employeeDataDTO);
        log.info("CHecked",setCheckboxValue(text));
//        employeeDataRepository.save(employeeDataDTO);
    }

    private EmployeeDataDTO createEmployeeData(String text) {
        EmployeeDataDTO employeeDataDTO = new EmployeeDataDTO();
        RetailerAddressDTO retailerAddressDTO = new RetailerAddressDTO();

        String firstNamePattern = "First Name\\s+(.*)";
        String lastNamePattern = "Last Name\\s+(.*)";
        String mobilePattern = "Mobile\\s+(.*)";
        String titlePattern = "Title\\s+(.*)";
        String emailPattern = "Email Address\\s+(\\w+@\\w+\\.\\w+)";
        String managerEmailPattern = "Manager's Email\\s+(\\w+@\\w+\\.\\w+)";
        String cdsidPattern = "CDSID\\s+([\\w-]+)";
        String dmsidPattern = "DMSID\\s+(\\w+)";
        String retailerPattern = "Retailer\\s+(.+)";
        String cityPattern = "City\\s+-\\s+(\\w+)";
        String statePattern = "State\\s+(\\w+)";
        String postcodePattern = "Postcode\\s+(\\w+)";
        String countryPattern = "Country\\s+(\\w+)";
        String startDatePattern = "Start Date\\s+(.*)";
        String finishDatePattern = "Finish Date\\s+(.*)";

        String[] lines = text.split("\n");
        for (String line : lines) {
            Matcher matcher;

            if (employeeDataDTO.getFirstName() == null) {
                matcher = Pattern.compile(firstNamePattern).matcher(line);
                if (matcher.find()) {
                    employeeDataDTO.setFirstName(matcher.group(1));
                }
            }

            if (employeeDataDTO.getLastName() == null) {
                matcher = Pattern.compile(lastNamePattern).matcher(line);
                if (matcher.find()) {
                    employeeDataDTO.setLastName(matcher.group(1));
                }
            }

            if (employeeDataDTO.getMobile() == null) {
                matcher = Pattern.compile(mobilePattern).matcher(line);
                if (matcher.find()) {
                    employeeDataDTO.setMobile(matcher.group(1));
                }
            }

            if (employeeDataDTO.getTitle() == null) {
                matcher = Pattern.compile(titlePattern).matcher(line);
                if (matcher.find()) {
                    employeeDataDTO.setTitle(matcher.group(1));
                }
            }

            if (employeeDataDTO.getEmailAddress() == null) {
                matcher = Pattern.compile(emailPattern).matcher(line);
                if (matcher.find()) {
                    employeeDataDTO.setEmailAddress(matcher.group(1));
                }
            }

            if (employeeDataDTO.getManagersEmail() == null) {
                matcher = Pattern.compile(managerEmailPattern).matcher(line);
                if (matcher.find()) {
                    employeeDataDTO.setManagersEmail(matcher.group(1));
                }
            }

            if (employeeDataDTO.getCDSID() == null) {
                matcher = Pattern.compile(cdsidPattern).matcher(line);
                if (matcher.find()) {
                    employeeDataDTO.setCDSID(matcher.group(1));
                }
            }

            if (employeeDataDTO.getDMSID() == null) {
                matcher = Pattern.compile(dmsidPattern).matcher(line);
                if (matcher.find()) {
                    employeeDataDTO.setDMSID(matcher.group(1));
                }
            }

            if (employeeDataDTO.getRetailer() == null) {
                matcher = Pattern.compile(retailerPattern).matcher(line);
                if (matcher.find()) {
                    employeeDataDTO.setRetailer(matcher.group(1));
                }
            }

            if (employeeDataDTO.getStartDate() == null) {
                matcher = Pattern.compile(startDatePattern).matcher(line);
                if (matcher.find()) {
                    employeeDataDTO.setStartDate(matcher.group(1));
                }
            }

            if (employeeDataDTO.getFinishDate() == null) {
                matcher = Pattern.compile(finishDatePattern).matcher(line);
                if (matcher.find()) {
                    employeeDataDTO.setFinishDate(matcher.group(1));
                }
            }

            matcher = Pattern.compile(cityPattern).matcher(line);
            if (matcher.find()) {
                retailerAddressDTO.setCity(matcher.group(1));
            }

            matcher = Pattern.compile(statePattern).matcher(line);
            if (matcher.find()) {
                retailerAddressDTO.setState(matcher.group(1));
            }

            matcher = Pattern.compile(postcodePattern).matcher(line);
            if (matcher.find()) {
                retailerAddressDTO.setPostCode(matcher.group(1));
            }

            matcher = Pattern.compile(countryPattern).matcher(line);
            if (matcher.find()) {
                retailerAddressDTO.setCountry(matcher.group(1));
            }
        }

        employeeDataDTO.setRetailerAddressDTO(retailerAddressDTO);
        return employeeDataDTO;
    }

    private static Object setCheckboxValue(String text) {
        AvanserOmniRewardsProgramsDTO rewardsProgramsDTO = new AvanserOmniRewardsProgramsDTO();
        RetailerDashBoardOneVoiceDTO dashBoardOneVoiceDTO = new RetailerDashBoardOneVoiceDTO();
        TrafficReportOminiStoreDTO reportOminiStoreDTO = new TrafficReportOminiStoreDTO();
        WorkPlaceSesimiDTO workPlaceSesimiDTO = new WorkPlaceSesimiDTO();
        String vf17Pattern = "(checked)\\s+VF1\\s+(.*)";
        String e2ePattern = "(checked)\\s+E2E\\s+(.*)";
        String webPromotionsPattern = "(Checked)\\s+Web Promotions\\s+(.*)";
        String mmlPattern = "(checked)\\s+MML\\s+(.*)";
        String ddpPattern = "(checked)\\s+DDP\\s+(.*)";
        String vidatiePattern = "(checked)\\s+VIDA/TIE\\s+(.*)";
        String cdsidPattern = "(checked)\\s+CDSID\\s+(.*)";
        String omniVisionPattern = "(checked)\\s+Omni Vision\\s+(.*)";
        String salesCloudPattern = "(checked)\\s+Sales Cloud\\s+(.*)";
        String contentStorePattern = "(checked)\\s+Content Store\\s+(.*)";
        String qw90Pattern = "(checked)\\s+QW90\\s+(.*)";
        String vistaAccessPattern = "(checked)\\s+VISTA Access\\s+(.*)";

        String[] lines = text.split("\n");
        for (String line : lines) {
            Matcher matcher;

            matcher = Pattern.compile(vf17Pattern).matcher(line);
            if (matcher.find()) {
                rewardsProgramsDTO.setVf17("checked".equals(matcher.group(1)));
            }

            matcher = Pattern.compile(e2ePattern).matcher(line);
            if (matcher.find()) {
                rewardsProgramsDTO.setE2e("checked".equals(matcher.group(1)));
            }

            matcher = Pattern.compile(webPromotionsPattern).matcher(line);
            if (matcher.find()) {
                rewardsProgramsDTO.setWebPromotions("checked".equals(matcher.group(1)));
            }

            matcher = Pattern.compile(mmlPattern).matcher(line);
            if (matcher.find()) {
                dashBoardOneVoiceDTO.setMml("checked".equals(matcher.group(1)));
            }

            matcher = Pattern.compile(ddpPattern).matcher(line);
            if (matcher.find()) {
                dashBoardOneVoiceDTO.setDdp("checked".equals(matcher.group(1)));
            }

            matcher = Pattern.compile(vidatiePattern).matcher(line);
            if (matcher.find()) {
                dashBoardOneVoiceDTO.setVida_tie("checked".equals(matcher.group(1)));
            }

            matcher = Pattern.compile(cdsidPattern).matcher(line);
            if (matcher.find()) {
                reportOminiStoreDTO.setCdsid("checked".equals(matcher.group(1)));
            }

            matcher = Pattern.compile(omniVisionPattern).matcher(line);
            if (matcher.find()) {
                reportOminiStoreDTO.setOminiVision("checked".equals(matcher.group(1)));
            }

            matcher = Pattern.compile(salesCloudPattern).matcher(line);
            if (matcher.find()) {
                reportOminiStoreDTO.setSalesCloud("checked".equals(matcher.group(1)));
            }

            matcher = Pattern.compile(contentStorePattern).matcher(line);
            if (matcher.find()) {
                workPlaceSesimiDTO.setContentStore("checked".equals(matcher.group(1)));
            }

            matcher = Pattern.compile(qw90Pattern).matcher(line);
            if (matcher.find()) {
                workPlaceSesimiDTO.setQw90("checked".equals(matcher.group(1)));
            }

            matcher = Pattern.compile(vistaAccessPattern).matcher(line);
            if (matcher.find()) {
                workPlaceSesimiDTO.setVistaAccess("checked".equals(matcher.group(1)));
            }
        }

        log.info("Reward programs:{}", rewardsProgramsDTO);
        log.info("DashBoard One Voice:{}", dashBoardOneVoiceDTO);
        log.info("Report Omini Store:{}", reportOminiStoreDTO);
        log.info("WorkPlace Sesimi:{}", workPlaceSesimiDTO);
        return null;
    }
}
