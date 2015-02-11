package cnr.ilc.t2k;

public enum Language {
	
	Italian{
		@Override
		public String toString() {
			return "IT";
		}
	},
	
	English{
		@Override
		public String toString() {
			return "GB";
		}
	};
 
	public static Language get(String lang){
		if(lang.equals(new String("GB"))){
			return Language.English;
		}
		return Language.Italian;
	}
}
