package com.dmm.task.data.repository;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dmm.task.data.entity.Tasks;
import com.dmm.task.form.TaskEditForm;
import com.dmm.task.service.AccountUserDetails;

@Service
@Transactional
public class TasksUpdate {
	
	@Autowired
	private TasksRepository taskrepo;
	
	public void update(AccountUserDetails user,TaskEditForm taskEditForm,int id) {
		
		
	    // データベースに登録する値を保持するインスタンスの作成
	    Tasks tasks = new Tasks();
			
	    // 画面から受け取った値を設定する
	    tasks.setId(id);
	    tasks.setName(user.getName());
	    tasks.setDate(taskEditForm.getDate());
	    tasks.setTitle(taskEditForm.getTitle());
	    tasks.setText(taskEditForm.getText());
	    tasks.setDone(taskEditForm.isDone());
			
	    // データベースを更新する
	    taskrepo.save(tasks);
	}

}
