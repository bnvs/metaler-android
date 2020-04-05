package com.bnvs.metaler.data.postsdummy


/*테스트용으로 쓰는 데이터클래스.
* 테스트할 때 안에 값들을 수정할 수 있기 때문에 읽기 쓰기가 가능한 var 로 선언함 */


data class PostDummyData(
    var id: Int,
    var user_id: Int,
    var category_id: Int,
    var title: String,
    var content: String,
    var price: Int,
    var price_type: String,
    var created_at: String,
    var updated_at: String,
    var profile_nickname: String,
    var tags: List<String>,
    var good_count: Int,
    var hate_count: Int,
    var is_bookmark: Boolean
)