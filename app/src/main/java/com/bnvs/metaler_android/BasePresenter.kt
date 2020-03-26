package com.bnvs.metaler_android

/**
 * 모든 Presenter 에 공통적으로 구현되어야 하는 BasePresenter 인터페이스이다.
 * 모든 Presenter 는 start() 함수를 가진다.
 * start() 는 OnCreate() 또는 OnResume() 에서
 * Presenter 가 시작할때 실행되는 함수이다.
 * */

interface BasePresenter {

    fun start()

}