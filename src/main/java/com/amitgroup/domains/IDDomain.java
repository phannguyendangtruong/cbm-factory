package com.amitgroup.domains;

import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amitgroup.models.ShareConstants;

@Component
public class IDDomain extends BaseDomain {

    @Autowired
    Hashids hashids;

    public String hashidsEncode(Long idToEncode){
        if (!ShareConstants.ApplicationSetting.IS_ENABLE_HASHIDS){
            return null;
        }

        try {
            return hashids.encode(idToEncode);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Long hashidsDecode(String valueToDecode){
        if (!ShareConstants.ApplicationSetting.IS_ENABLE_HASHIDS){
            return null;
        }

        try {
            long[] decodedVectors = hashids.decode(valueToDecode);
            if (decodedVectors.length < 1){
                return null;
            }
            return decodedVectors[0];
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
