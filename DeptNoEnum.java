/**
 * 枚举
 * */
public enum DeptNoEnum {
	
	
	DEPT_WEALTH_MANAGE_SOCIAL_FINANCE(1, "财富管理部社交金融组"), DEPT_PUBLIC_RELATION(2, "公关部"), DEPT_WEALTH_MANAGE_CONTENT(3,
			"财富管理内容部");

	private Integer code;
	private String name;

	private DeptNoEnum(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public static void main(String[] args) {
		// 循环展示枚举类型
		for (DeptNoEnum deptNoEnum : DeptNoEnum.values()) {
			String a1 = deptNoEnum.toString();
			System.out.println(DeptNoEnum.valueOf(a1).getCode());
			System.out.println(DeptNoEnum.valueOf(a1).getName());
		}
	}
	public static String getNameByCode(Integer code) {
		for (DeptNoEnum deptNoEnum : DeptNoEnum.values()) {
			if (deptNoEnum.getCode().equals(code)) {
				return deptNoEnum.getName();
			}
		}
		return null;
	}

	
}
