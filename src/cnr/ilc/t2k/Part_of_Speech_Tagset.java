package cnr.ilc.t2k;

public enum Part_of_Speech_Tagset {

	Coordinatingconjunction{
		@Override
		public String toString() {
			return "[CC]";
		}
	},
	
	Propernounplural{
		@Override
		public String toString() {
			return "[NNPS]";
		}
	},
	
	Adjective{
		@Override
		public String toString() {
			return "[JJ]";
		}
	},
	
	
	
	Nounplural{
		@Override
		public String toString() {
			return "[NNS]";
		}
	},
	
	Possessiveending{
		@Override
		public String toString() {
			return "[POS]";
		}
	},
	
	Propernounsingular{
		@Override
		public String toString() {
			return "[NNP]";
		}
	},
	
	Prepositionconjunction{
		@Override
		public String toString() {
			return "[IN]";
		}
	},
	
	Nounsingular{
		@Override
		public String toString() {
			return "[NN]";
		}
	};


}
