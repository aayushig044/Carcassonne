import java.util.Arrays;

/*
 * TODO
 * -Define properties:
 * 		river connection
 * 		road  connection
 * 		castle/farmland handling
 * -Meeples
 * -Connection validations
 * 		ex) can this piece placed here?
 */

/*
 * NW  N  NE
 * W   C   E
 * SW  S  SE
 *
 * More likely
 * EX) 1.png
 * Road: S, E
 * River: null
 * City: null
 * 
 * EX) 28.png
 * Road: null
 * River: null
 * City: N, E, S
 * 
 * EX) 40.png
 * Road: null
 * River: W, S
 * City: N, E
 * 
 * 
 * 
 * Maybe
 * EX) 1.png
 * G  G  G
 * G  R  R
 * G  R  G
 * 
 * EX) 28.png
 * G  C  C
 * G  C  C
 * G  C  C
 * 
 * EX) 40.png
 * C  C  C
 * R  R  C
 * G  R  C
 * 
 * Maybe 
 * EX) 1.png
 * N: F  F  F
 * W: F  F  F
 * S: F  R  F
 * E: F  R  F
 * 
 * EX) 28.png
 * N: C  C  C
 * W: F  F  F
 * S: C  C  C
 * E: C  C  C
 * 
 * EX) 40.png
 * N: C  C  C
 * W: F  R  F
 * S: F  R  F
 * E: C  C  C
 * 
 *    C  C  C
 * F  F  F  F  C
 * R  R  R  F  C
 * F  F  R  F  C
 *    F  R  F
 *    
 * 
 */
enum Rotation {
	D0(0),
	D90(1),
	D180(2),
	D270(3);

	private final int id;
	Rotation(int id) {
		this.id = id;
	}
}

enum Orient {
	N(0), W(1), S(2), E(3);

	private final int id;
	Orient(int id) {
		this.id = id;
	}
	Orient fromId(int i) {
		for(Orient o: Orient.values())
			if(o.id == i)
				return o;
		return null;
	}
	Orient opposite() {
		return this.fromId((this.id + 2)%4);
	}
	/*
		Visualization
           F  C  F            F  F  C
		 C    N    F        F    E    F
		 F  E   W  F -D90-> R  S   N  C
		 F    S    C        F    W    F
		   F  R  F            C  F  F

		N -> E
		W -> N
		...

           F  C  F             F  R  F
		 C    N    F         C    S    F
		 F  E   W  F -D180-> F  W   E  F
		 F    S    C         F    N    C
		   F  R  F             F  C  F

		N -> S
		W -> E
		...

           F  C  F             F  F  C
		 C    N    F         F    W    F
		 F  E   W  F -D270-> C  N   S  R
		 F    S    C         F    E    F
		   F  R  F             C  F  F

		N -> W
		W -> S
		...
	 */
	Orient rotate(Rotation r) {
		return this.fromId((this.id - r.ordinal() + 4) % 4);
	}
};

enum TerrainType {
	Farm, River, City, Road
};

class Side {
	TerrainType[] sidetypes;

	Side(TerrainType l, TerrainType m, TerrainType r) {
		sidetypes = new TerrainType[3];
		sidetypes[0] = l;
		sidetypes[1] = m;
		sidetypes[2] = r;
	}

	public TerrainType[] getSide() {
		return this.sidetypes;
	}

	public boolean equals(Object obj) {
		return Arrays.equals(this.sidetypes, ((Side) obj).getSide());
	}
}

public class CarcassonneTile {
	Rotation rotation;
	Side[] sides;

	public CarcassonneTile(Side[] sides) {
		this.rotation = Rotation.D0;
		this.sides = new Side[4];
		this.sides = sides;
	}
	
	public Side[] getSides() {
		return this.sides;
	}

	public void rotate(Rotation r) {
		this.rotation = r;
	}

	public Side[] getRotatedSides() {
		return new Side[] {
				this.sides[Orient.N.rotate(this.rotation).ordinal()],
				this.sides[Orient.W.rotate(this.rotation).ordinal()],
				this.sides[Orient.S.rotate(this.rotation).ordinal()],
				this.sides[Orient.E.rotate(this.rotation).ordinal()],
		};
	}
	
	public boolean fit(CarcassonneTile t, Orient o, Rotation r) {
		return this.sides[o.ordinal()].equals(t.getSides()[o.rotate(r).opposite().ordinal()]);
	}

	public String toString() {
		Side[] rotated = this.getRotatedSides();
		return String.format("      %s    %s    %s      \n" +
				"%s                          %s\n" +
				"%s                          %s\n" +
				"%s                          %s\n" +
				"      %s    %s    %s      \n",
				rotated[0].getSide()[0], rotated[0].getSide()[1], rotated[0].getSide()[2],
				rotated[1].getSide()[2], rotated[3].getSide()[0],
				rotated[1].getSide()[1], rotated[3].getSide()[1],
				rotated[1].getSide()[0], rotated[3].getSide()[2],
				rotated[2].getSide()[0], rotated[2].getSide()[1], rotated[2].getSide()[2]);
	}

	public static void main(String[] args) {
		CarcassonneTile t1 = new CarcassonneTile(new Side[] {
				new Side(TerrainType.Farm, TerrainType.Farm, TerrainType.Farm), //N
				new Side(TerrainType.Farm, TerrainType.Farm, TerrainType.City), //W
				new Side(TerrainType.Farm, TerrainType.Farm, TerrainType.Farm), //S
				new Side(TerrainType.Farm, TerrainType.Farm, TerrainType.Farm)  //E
		});
		
		CarcassonneTile t2 = new CarcassonneTile(new Side[] {
				new Side(TerrainType.Farm, TerrainType.Farm, TerrainType.City), //N
				new Side(TerrainType.Farm, TerrainType.Farm, TerrainType.Farm), //W
				new Side(TerrainType.Farm, TerrainType.Farm, TerrainType.Farm), //S
				new Side(TerrainType.Farm, TerrainType.Farm, TerrainType.Farm)  //E
		});
		/*
		   F  F  F      F  F  F
		 F    N    F  F    N    F
		 F  E   W  F  F  E   W  F
		 F    S    C  C    S    F
		   F  F  F      F  F  F
		 */
		System.out.println("match D0  ? :" + t1.fit(t2, Orient.W, Rotation.D0));
		System.out.println("match D90 ? :" + t1.fit(t2, Orient.W, Rotation.D90));
		System.out.println("match D180? :" + t1.fit(t2, Orient.W, Rotation.D180));
		System.out.println("match D270? :" + t1.fit(t2, Orient.W, Rotation.D270));
		System.out.println(t1);
		t2.rotate(Rotation.D90);
		System.out.println(t2);
	}
}
