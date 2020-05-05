### [Domain-Driven Design의 적용](http://aeternum.egloos.com/page/109) 시리즈를 공부하며 정리한 내용입니다.

### [예제](https://github.com/sky7th/domain-driven-design/tree/master/src/main/java/com/sky7th/domaindrivendesign/part2)

# PART 2. AGGREGATE와 REPOSITORY

## 흔하디 흔한 주문 도메인
REFERENCE OBJECT는 독립적인 클래스로 표기

VALUE OBJECT는 클래스의 속성으로 표기

### 한도 초과 여부를 검증하는 책임을 어떤 도메인 객체에게 할당해야 할까?

#### 고객 객체에 할당할 경우
- 고객 객체가 주문 객체의 내부 상태를 알고 있어야 하므로 양방향 연관 관계가 발생한다.
- 정보를 가지고 있는 클래스에 책임을 할당하라는 INFORMATIN EXPERT 패턴에 위반된다.

#### 양방향 연관 관계의 문제점
- 도메인 모델 간의 결합도를 높임
- 관계 간의 일관성을 유지하기 위해 필요한 구현 복잡도가 증가

#### 따라서, 한도액 검증 책임은 주문 객체에게 할당하는 것이 적절하다.

### 주문에 주문 항목을 추가하는 시나리오 
1. 고객은 상품을 선택하고 상품의 개수를 입력한다. 
2. 시스템은 상품과 개수를 가진 주문 항목을 생성하고 주문에 추가한다. 
3. 주문은 새로 추가된 주문 항목의 가격을 더한 주문 총액과 구매 고객의 한도액을 비교한다. 
4. 만약 한도를 초과했다면 예외를 발생시키고 주문 프로세스를 중단한다.

#### 주문 객체와 주문 항목 객체 간의 긴밀한 협력이 필요
- 한도액을 검증하기 위함이다.
- 상태 변경 시 이들이 하나의 단위로 취급되어야 한다.
- 구매액이 고객의 주문 한도액을 초과할 수 없다는 불변식(invariant)을 공유하는 하나의 논리적 단위라고 할 수 있다.


#### 주문은 주문 항목을 캡슐화해야 한다.

#### 다중 쓰레드 환경일 때 문제
- 일관성 문제를 야기한다.
- 각각의 사용자 입장에서는 주문에 대한 불변식이 지켜졌으나 미묘한 동시성 문제로 인해 전체 시스템의 관점에서 보면 무결성이 깨져 버린다.

#### 다중 쓰레드 환경일 때 신경써야 할 점
- 주문-주문 항목 클러스터에 대한 배타적인 접근이 가능해야 한다.
- 주문 항목은 항상 주문을 통해서만 접근 가능해야 한다.

## AGGREGATE
> 데이터 변경 시 하나의 단위로 취급할 수 있는 연관된 객체들의 클러스터

변경에 대한 불변식을 유지하기 위해 하나의 단위로 취급되면서 변경의 빈도가 비슷하고, 동시 접근에 대한 잠금의 단위가 되는 객체의 집합이다.

### AGGREGATE 특징
- 루트와 경계를 가진다. 
- 루트: AGGREGATE 내에 포함된 하나의 REFERENCE OBJECT이며 ENTRY POINT 라고도 한다. 그리고 외부에서 참조 가능한 유일한 AGGREGATE의 내부 객체이다.
- 경계: AGRREGATE 내부에 무엇이 포함되어야 하는지를 정의한다.
- AGGREGATE 내부의 객체는 외부 객체를 자유롭게 참조할 수 있다.

<details markdown="1">
<summary style="font-weight: bold;">Eric Evans가 정리한 AGGREGATE 패턴에 대한 규칙 (4번 규칙 중요)</summary>

1. ENTRY POINT는 전역 식별자(global identity)를 가지며 궁극적으로 불변식(invariant)을 검증하는 책임을 가진다.

2. ENTRY POINT는 전역 식별자(global identity)를 가진다. AGGREGATE 내부에 속한 REFERENCE OBJECT들은 지역 식별자(local identity)를 가지며, 지역 식별자는 AGGREGATE 내부에서만 유일하다.

3. AGGREGATE 경계 외부에 있는 어떤 객체도 ENTRY POINT 이외의 AGGREGATE 내부 객체를 참조할 수 없다. ENTRY POINT는 내부에 속한 REFERENCE OBJECT를 외부에 전달할 수는 있지만 이를 전달 받은 객체는 일시적으로만 사용할 뿐 이에 대한 참조를 유지하지 않는다. ENTRY POINT는 VALUE OBJECT에 대한 복사본을 다른 객체에게 전달할 수 있다. VALUE OBJECT는 단지 값일 뿐이며, VALUE OBJECT는 AGGREGATE와 연관관계를 가지지 않기 때문에 VALUE OBJECT에 어떤 일이 발생하는지에 대해서는 신경 쓰지 않는다.

4. **위 규칙으로부터 오직 ENTRY POINT만이 REPOSITORY로부터 직접 얻어질 수 있다는 사실을 유추할 수 있다. 모든 다른 객체들은 ENTRY POINT로부터의 연관 관계 항해를 통해서만 접근 가능하다.**

5. AGGREATE 내부의 객체들은 다른 AGGREGATE의 ENTRY POINT를 참조할 수 있다.


6. 삭제 오퍼레이션은 AGGREGATE 내부의 모든 객체를 제거해야 한다.(가비지 컬렉션을 가진 언어의 경우 이 규칙을 준수하는 것이 용이하다. AGGREGATE 외부의 어떤 객체도 ENTRY POINT를 제외한 내부 객체를 참조하지 않기 때문에 ENTRY POINT를 제거하면 이에 수반된 모든 내부 객체가 제거될 것이다.)


7. AGGREGATE 내부의 어떤 객체에 대한 변경이 확약되면, 전체 AGGREGATE에 관한 모든 불변식이 만족되어야 한다.
</details>

<br/>

#### 시스템 내의 모든 주문이 특정 고객 객체를 얻은 후에만 접근할 수 있다면
- 주문과 주문 항목은 고객 객체를 ENTRY POINT로 하는 AGGREGATE의 일부가 되어야 한다.

#### 고객과 무관하게 주문에 직접 접근해야 할 필요가 있다면
- 주문을 ENTRY POINT로 하는 AGGREGATE를 만드는 것이 좋다.

### AGGREGATE 장점
- AGGREGATE와 ENTRY POINT 역시 시스템의 복잡도를 낮춰주는 유용한 기법이다.
- 불변식(invariant)을 공유하는 도메인 클래스들의 클러스터에 집중할 수 있다.

### 구현과 관련된 AGGREGATE 장점
- 미묘한 동시성 컨텍스트 하에서 일관성을 유지하기 위해 도메인 클래스의 잠금 전략을 적용할 수 있는 위치를 제공한다.
- 높은 경합 지점의 식별을 통해 전반적인 성능 향상을 꾀할 수 있는 기초 자료가 된다.

### REPOSITORY
- REPOSITORY는 AGGREGATE의 ENTRY POINT에 대해서만 할당한다.

### AGGREGATE, ENTRY POINT, REPOSITORY 공통점
- 유용한 분석 기법이다.
- 도메인 객체에 대한 메모리 컬렉션 관점을 데이터베이스와 동시 실행 컨텍스트를 기반으로 한 엔터프라이즈 어플리케이션 환경으로 자연스럽게 이어주는 구현 기법이기도 하다.