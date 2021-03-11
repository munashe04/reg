package com.thondo.whatsappconnect.service;

import com.thondo.whatsappconnect.entity.MainProfile;
import com.thondo.whatsappconnect.enums.ApplicationStatus;
import com.thondo.whatsappconnect.enums.MenuEnum;
import com.thondo.whatsappconnect.repository.MainProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MessageProcessorService {
    @Autowired
    private MainProfileRepository mainProfileRepository;

    public String processMessage(MultiValueMap<String, String> paramMap) {
        String formattedBody = null;
        MainProfile mainProfile = new MainProfile();

        if (paramMap != null && paramMap.get("WaId") != null && mainProfile.getMenuStage() == null) {
            System.out.println(" received the mobileNumber  >>>>>>>>>>>>>>>>>" + paramMap.get("WaId"));
            String mobileNumber = paramMap.get("WaId").get(0);
            System.out.println(" received the mobileNumber >>>>>>>>>>>>>>>>>" + mobileNumber);
            String formatedMobileNumber = mobileNumber.replace('[', ' ').replace(']', ' ').trim();
            System.out.println(" received the formated mobileNumber >>>>>>>>>>>>>>>>>" + formatedMobileNumber);
            if (paramMap != null && paramMap.get("Body") != null) {
                String body = paramMap.get("Body").get(0);
                System.out.println(" received the msg with body >>>>>>>>>>>>>>>>>" + paramMap.get("Body"));
                formattedBody = body.replace('[', ' ').replace(']', ' ').trim();
                System.out.println(" received the formated body >>>>>>>>>>>>>>>>>" + body);
            }

            Optional<MainProfile> byMobileNumber = mainProfileRepository.findByMobileNumber(formatedMobileNumber);

            if (byMobileNumber.isPresent() && byMobileNumber.get().getMenuStage().equals("")) {

                return "Hi " + byMobileNumber.get().getFullName() + "More Menus will follow";
            }
            if (!byMobileNumber.isPresent() && formattedBody != null && formattedBody.equalsIgnoreCase("1")) {
                System.out.println(byMobileNumber.get().getMenuStage());
               // MainProfile mainProfile1 = new MainProfile();
                mainProfile.setApprovalStatus(ApplicationStatus.PENDING.getValue());
                mainProfile.setMobileNumber(formatedMobileNumber);
                mainProfile.setDateCreated(LocalDateTime.now());
                mainProfile.setMenuStage("second");
                // mainProfile.setFullName("");
                mainProfile.setMenuSelected(MenuEnum.REGISTRATION.getValue());
                mainProfileRepository.save(mainProfile);
                 System.out.println(byMobileNumber.get().getMenuStage());
                return "Please enter full name [name and surname]";
            }
            if (paramMap.get("Body") == null) {
                //main menu
                return "Welcome to Zunde CF1 \n select 1 to register ";
            }
        }


            // MY ADDITIONALS
//Optional<MainProfile> byMenuStage = mainProfileRepository.findByMenuStage("second");
        String mobileNumber = paramMap.get("WaId").get(0);
        String formatedMobileNumber = mobileNumber.replace('[', ' ').replace(']', ' ').trim();
        Optional<MainProfile> byMobileNumber = mainProfileRepository.findByMobileNumber(formatedMobileNumber);

        System.out.println(byMobileNumber.get().getMenuStage());

            if (byMobileNumber.isPresent() && byMobileNumber.get().getMenuStage().equals("second")) {
                // Optional<MainProfile> byName = mainProfileRepository.findByFullName(formatedFullName);
                String fullName = paramMap.get("Body").get(0);
                String formatedFullName = fullName.replace('[', ' ').replace(']', ' ').trim();
                mainProfile.setApprovalStatus(ApplicationStatus.PENDING.getValue());
                mainProfile.setMenuSelected(MenuEnum.REGISTRATION.getValue());
                mainProfile.setMenuStage("third");
                mainProfile.setFullName(formatedFullName);
                System.out.print(mainProfile.getFullName());
                mainProfileRepository.save(mainProfile);
                return "Enter Your Gender";

            }

            String gender = paramMap.get("Body").get(0);
            String formatedGender = gender.replace('[', ' ').replace(']', ' ').trim();
            Optional<MainProfile> byGender = mainProfileRepository.findByGender(formatedGender);
            if (paramMap != null && paramMap.get("Body") != null && byMobileNumber.get().getMenuStage().equals("third")) {
                if (paramMap.get("Body") != null) {
                    //  MainProfile mainProfile = new MainProfile();
                    mainProfile.setApprovalStatus(ApplicationStatus.PENDING.getValue());
                    mainProfile.setMenuSelected(MenuEnum.REGISTRATION.getValue());
                    mainProfile.setMenuStage("forth");
                    //  mainProfile.setFullName();
                    mainProfile.setGender(formatedGender);
                    mainProfileRepository.save(mainProfile);
                    return "Enter The Number Of The Person Who Reffered You";
                }
            } else if (paramMap != null && paramMap.get("Body") == null && !byGender.isPresent()) {
                return "Enter your Gender2";
            }

            //String gender = paramMap.get("Body").get(0);
            //  String formatedGender = gender.replace('[', ' ').replace(']', ' ').trim();
            // Optional<MainProfile>byGender = mainProfileRepository.findByGender(formatedGender);
            if (paramMap.get("Body") != null) {
                // MainProfile mainProfile = new MainProfile();
                String refferedBy = paramMap.get("Body").get(0);
                String formatedRef = refferedBy.replace('[', ' ').replace(']', ' ').trim();
                mainProfile.setRefferedBy(formatedRef);
                mainProfile.setApprovalStatus(ApplicationStatus.PENDING.getValue());
                mainProfile.setMenuSelected(MenuEnum.REGISTRATION.getValue());
                mainProfile.setMenuStage("forth");
                mainProfileRepository.save(mainProfile);
                return "Enter Your Date Of Birth";
            }

            if (byGender.isPresent() && byGender.get().getMenuStage() == "forth") {
                MainProfile profile = byGender.get();
                if (profile.getMenuStage() == "forth" && formattedBody != null && !formattedBody.isEmpty()) {
                    profile.setFullName(formattedBody);
                    profile.setMenuStage("fifth");
                    mainProfileRepository.save(profile);
                }
                return "Thank you " + profile.getFullName() + "We have successfully registered your profile";
            }

            return "Welcome to Zunde CF1 \n select 1 to register ";


            // return "Welcome to Zunde CF1 \n select 1 to register ";


        }
      //  return "Welcome to Zunde CF1 \n select 1 to register ";
    }





