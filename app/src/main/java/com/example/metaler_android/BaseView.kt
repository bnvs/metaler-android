package com.example.metaler_android

/**
 * 모든 View 에 공통적으로 구현되어야 하는 BaseView 인터페이스이다.
 * 모든 View 는 Presenter 객체를 지녀야한다.
 * View 에 들어갈 Presenter 의 타입을 제네릭으로 미리 지정해준다.
 * */

interface BaseView<T> {

    var presenter: T
    
}