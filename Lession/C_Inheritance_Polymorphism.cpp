#include <iostream>
using namespace std;

class Base {
  private:
    int pvt = 1;

  protected:
    int prot = 2;

  public:
    int pub = 3;

    // function to access private member
    int getPVT() {
      return pvt;
    }
};

class PublicDerived : public Base {
  public:
    // function to access protected member from Base
    int getProt() {
      return prot;
    }
};

int main() {
  PublicDerived object1;
  cout << "Private = " << object1.getPVT() << endl;
  cout << "Protected = " << object1.getProt() << endl;
  cout << "Public = " << object1.pub << endl;
  return 0;
}



#include <iostream>
using namespace std;

class Base {
  private:
    int pvt = 1;

  protected:
    int prot = 2;

   public:
    int pub = 3;

    // function to access private member
    int getPVT() {
      return pvt;
    }
};

class ProtectedDerived : protected Base {
  public:
    // function to access protected member from Base
    int getProt() {
      return prot;
    }

    // function to access public member from Base
    int getPub() {
      return pub;
    }
};

int main() {
  ProtectedDerived object1;
  cout << "Private cannot be accessed." << endl;
  cout << "Protected = " << object1.getProt() << endl;
  cout << "Public = " << object1.getPub() << endl;
  return 0;
}


#include <iostream>
using namespace std;

class Base {
  private:
    int pvt = 1;

  protected:
    int prot = 2;

  public:
    int pub = 3;

    // function to access private member
    int getPVT() {
      return pvt;
    }
};

class PrivateDerived : private Base {
  public:
    // function to access protected member from Base
    int getProt() {
      return prot;
    }

    // function to access private member
    int getPub() {
      return pub;
    }
};

int main() {
  PrivateDerived object1;
  cout << "Private cannot be accessed." << endl;
  cout << "Protected = " << object1.getProt() << endl;
  cout << "Public = " << object1.getPub() << endl;
  return 0;
}



#include <iostream>
#include <string>
using namespace std;

// Base class
class Vehicle {
  public: 
    string brand = "HONDA";
    void func() {
      cout << "I am HONDA \n" ;
    }
};

// Derived class
class Car: public Vehicle {
  public: 
    string model = "CIVIC";
};

int main() {
  Car myCar;
  myCar.func();
  cout << myCar.brand + " " + myCar.model;
  return 0;
}



#include <iostream>
using namespace std;

// Parent class
class MyClass {
  public: 
    void myFunction() {
      cout << "Some content in parent class." ;
    }
};

// Child class
class MyChild: public MyClass {
  public:
    void myFunction() {
      cout << "Some content in sub parent class." ;
    }
};

// Grandchild class 
class MyGrandChild: public MyChild {
};

int main() {
  MyGrandChild myObj;
  myObj.myFunction();
  return 0;
}



#include <iostream>
using namespace std;

// Base class
class MyClass {
  public:
    void myFunction() {
      cout << "Some content in parent class.\n" ;
    }
};

// Another base class
class MyOtherClass {
  public:
    void myOtherFunction() {
      cout << "Some content in another class.\n" ;
    }
};

// Derived class
class MyChildClass: public MyClass, public MyOtherClass {
};

int main() {
  MyChildClass myObj;
  myObj.myFunction();
  myObj.myOtherFunction();
  return 0;
}



#include <iostream>
using namespace std;

// Base class
class Employee  {
  protected:  // Protected access specifier
    int salary;
};

// Derived class
class Programmer: public Employee {
  public:
    int bonus;
    void setSalary(int s) {
      salary = s;
    }
    int getSalary() {
      return salary;
    }
};

int main() {
  Programmer myObj;
  myObj.setSalary(50000);
  myObj.bonus = 15000;
  cout << "Salary: " << myObj.getSalary() << "\n";
  cout << "Bonus: " << myObj.bonus << "\n";
  return 0;
}



#include <iostream>
#include <string>
using namespace std;

// Base class
class Animal {
  public:
    void animalSound() {
      cout << "The animal makes a sound \n";
    }
};

// Derived class
class Pig : public Animal {
  public:
    void animalSound() {
      cout << "The pig says: wee wee \n";
    }
};

// Derived class
class Dog : public Animal {
  public:
    void animalSound() {
      cout << "The dog says: bow wow \n";
    }
};

int main() {
  Animal myAnimal;
  Pig myPig;
  Dog myDog;

  myAnimal.animalSound();
  myPig.animalSound();
  myDog.animalSound();
  return 0;
}



#include <iostream>
#include <string>
using namespace std;
class A
{
	float d;
    public:
	int a;
	void change(int i){
		a = i;
	}
	void value_of_a(){
		cout<<a;
	}
};
 
class B: public A
{
	int a = 15;
    public:
	void print(){
		cout<<a;
	}
};
 
int main(int argc, char const *argv[])
{
	B b;
	b.change(10);
	b.print();
	b.value_of_a();
 
	return 0;
}



#include <iostream>
#include <string>
using namespace std;
class A
{
	float d;
	public:
	A(){
		cout<<"Constructor of class A\n";
	}
};
 
class B: public A
{
	int a = 15;
    public:
	B(){
		cout<<"Constructor of class B\n";
	}
};
 
int main(int argc, char const *argv[])
{
	B b;
	return 0;
}


#include <iostream>
#include <string>
using namespace std;
class A{
	float d;
	public:
	virtual void func(){
		cout<<"Hello this is class A\n";
	}
};
 
class B: public A{
	int a = 15;
public:
	void func(){
		cout<<"Hello this is class B\n";
	}
};
 
int main(int argc, char const *argv[])
{
	B b;
	b.func();
	return 0;
}


#include <iostream>
#include <string>
using namespace std;
class A
{
	float d;
    public:
	virtual void func(){
		cout<<"Hello this is class A\n";
	}
};
 
class B: public A
{
	int a = 15;
	public:
	void func(){
		cout<<"Hello this is class B\n";
	}
};
 
int main(int argc, char const *argv[])
{
	A *a = new A();
	a->func();
	return 0;
}


#include <iostream>
#include <string>
using namespace std;
class A
{
	float d;
	public:
	virtual void func(){
		cout<<"Hello this is class A\n";
	}
};
 
class B: public A
{
	int a = 15;
	public:
	void func(){
		cout<<"Hello this is class B\n";
	}
};
 
int main(int argc, char const *argv[])
{
	A *a = new A();
	B b;
	a = &b;
	a->func();
	return 0;
}



#include <iostream>
using namespace std;
 
class Base1 {
	public:
		Base1()
		{ cout << " Base1" << endl;  }
};
  
class Base2 {
	public:
		Base2()
		{ cout << "Base2" << endl;  }
};
  
class Derived: public Base1, public Base2 {
   public:
     Derived()
     {  cout << "Derived" << endl;  }
};
  
int main()
{
   Derived d;
   return 0;
}


#include <iostream>
using namespace std;
   class Base1 {
 public:
     ~Base1()  { cout << " Base1" << endl; }
};
   
class Base2 {
 public:
     ~Base2()  { cout << " Base2" << endl; }
};
   
class Derived: public Base1, public Base2 {
   public:
     ~Derived()  { cout << " Derived" << endl; }
};
   
int main()
{
   Derived d;
   return 0;
} 


#include <iostream>

using namespace std;
class Base {};
class Derived: public Base {};
 
int main()
{
    Base *p = new Derived;
    Derived *q = new Base;
}


#include <iostream>

using namespace std;
class Base
{
public:
    int lfc()  { cout << "Base::lfc() called"; }
    int lfc(int i)  { cout << "Base::lfc(int i) called"; }
};
 
class Derived: public Base
{
public:
    int lfc() {  cout << "Derived::lfc() called"; }
};
 
int main()
{
    Derived d;
    d.lfc(5);
    return 0;
}



#include <iostream>

using namespace std;
class find {
public:
   void print()  { cout <<" In find"; }
};
  
class course : public find {
public:
   void print() { cout <<" In course"; }
};
  
class tech: public course { };
  
int main(void)
{
  tech t; 
  t.print();
  return 0;
}


