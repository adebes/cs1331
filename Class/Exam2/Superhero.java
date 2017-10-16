public abstract class Superhero{
	public abstract void fightCrime();
	public void canFight(){
		System.out.println("Superhero canFight");
	}
}
class Avengers extends Superhero{
	public void describeCostume(){
		System.out.println("Avengers describeCostume");
	}
	public void fightCrime(){
		System.out.println("Avengers fightCrime");
	}
}
class Hulk extends Avengers implements Powerful{
	public void describeCostume(){
		System.out.println("Hulk describeCostume");
	}
	public void smash(){
		System.out.println("Hulk Smash");
	}
}
interface Powerful {
	/*public abstract*/void smash();
}
