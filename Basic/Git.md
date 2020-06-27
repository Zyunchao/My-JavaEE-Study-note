# Git 常用命令

## 本地仓库常用命令

- **git inti**：初始化本地仓库
- **git status**：查看本地仓库状态
- **git add**：将修改内容添加到暂存区
  > 后面跟文件名：添加指定文件到暂存区
  > **.** ：添加所有修改文件
- **git commit**：将暂存区中的内容提交到本地仓库

  > **-m**：添加备注

- **git log**：显示所有提交过的版本信息

  > --pretty=online：日志展示形式

- **git reflog**：可以查看所有分支的所有操作记录（包括已经被**删除的** commit 记录和 reset 的操作）

### 版本回退

- **git reset --hard** 版本号

### 分支

- **git branch**：分支操作

  > 跟分支名：创建版本库
  > 无参数：查看当前版本库的分支
  > **git branch -d dev**：删除指定分支

- **git checkout <name>**：切换分支

  > **git checkout -b <name>** ：创建+切换分支

- **git switch <name>**：切换分支
  > **git switch -c <name>**：创建+切换分支

### 远程仓库

协同开发

#### 关联远程仓库

- **git remote add** 远程名称 远程仓库 URL
- **git push -u** 仓库名称 分支名
