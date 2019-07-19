 //控制层 
app.controller('searchController' ,function($scope,$controller,searchService){	
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.search=function(){
		searchService.search($scope.keys).success(
			function(response){
				$scope.list=response;
			}			
		);
	}    
});	