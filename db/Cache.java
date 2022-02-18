package db;

import java.util.HashMap;
import java.util.Map;

public class Cache {
	
	    Map<Long,CustomerInfo> customerMap=new HashMap<>();
	    Map<Long,Map<Long, AccountInfo>> accountMap=new HashMap<>();
	    long idNo;
	    long accNo;
	      
	    public void storeInCache(Map<Long,Map<Long, AccountInfo>> inpAccountMap,long inpAccNumber,long idNo,Map<Long, CustomerInfo> customerMap)
	    {
	    	this.accountMap=inpAccountMap;
	    	this.customerMap=customerMap;
	    	this.accNo=inpAccNumber;
	    	this.idNo=idNo;
	    }
}
