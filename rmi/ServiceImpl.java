package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author  liyc
 * @date 2016年9月13日 下午2:21:59
*/
public class ServiceImpl extends UnicastRemoteObject implements IService {  

	/** 
	 */
	private static final long serialVersionUID = 682805210518738166L;

	/**
	 * @throws RemoteException
	 */
	protected ServiceImpl() throws RemoteException {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.suning.ebuy.wd.web.IService#queryName(java.lang.String)
	 */
	@Override
	public String queryName(String no) throws RemoteException {
		// 方法的具体实现
		System.out.println("hello" + no);
		return String.valueOf(System.currentTimeMillis());
	}

}