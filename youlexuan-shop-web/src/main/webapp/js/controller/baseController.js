app.controller('baseController',function($scope){
	// 重新加载数据
	$scope.reloadList = function(){
		$scope.search($scope.paginationConf.currentPage,
				$scope.paginationConf.itemsPerPage);
	};
	
	// 分页的属性
	$scope.paginationConf={
			// 当前默认是第一页
			currentPage:1,
			// 总条目数，先定死，后面会动态修改
			totalItems:10,
			// 每页的展示的条目数
			itemsPerPage:10,
			// 切换每页显示的数据
			perPageOptions:[10,20,30],
			onChange:function(){
				$scope.reloadList();
			}
		};
	// 获取复选框选中的id
	$scope.selectIds = [];
	
	$scope.updataSelection = function($event,id){
		if($event.target.checked){
			$scope.selectIds.push(id);
		}else{
			var idx = $scope.selectIds.indexOf(id);
			$scope.selectIds.splice(idx,1);
		}
	};
	
	// 全选
	$scope.checkAll = function(){
		if(!$scope.select_all){
			$scope.select_one = true;
			
		}else{
			$scope.select_one = false;
		}
	};
	
	// json解析
	$scope.jsontostring = function(jsonstring,key){
		var json = JSON.parse(jsonstring);
		var value = ""; 
		for(var i = 0; i < json.length; i ++){
			if(i > 0){
				value += "   "
			}
			value += json[i][key];  
		}
		
		return value;
	};
	
	// 模拟数据源
//	$scope.brandlist1 = {data:[{id:1,text:'xixi'},{id:2,name:'haha',text:'重量1'},{id:3,name:'heihie',text:'重量2'}]};
	
	
	
});























