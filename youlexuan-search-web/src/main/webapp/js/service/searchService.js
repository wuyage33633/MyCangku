//服务层
app.service('searchService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.search=function(keys){
		return $http.get('../itemsearch/search.do?keys='+keys);		
	}
	
});
