import React from 'react';
import { Input, Button, Modal } from 'antd';
import QuestionForm from './QuestionForm';
import {
    DeleteOutlined,
    EditOutlined,
    PlusCircleOutlined,
    CarryOutOutlined,
} from '@ant-design/icons';
import { createPoll } from './client';

class Main extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            title: '',
            editMod: false,
            currentEditId: -1,
            questions: [],
        };
    }

    componentDidMount(){}

    openEditMod = (QuestionId) => {

        this.setState({
            editMod: true,
            currentEditId: QuestionId,
        });
    };

    closeEditMod = () => {
        this.setState({
            editMod: false,
        });
    }

    onQuestionConfirm = (data) => {
        if (this.state.currentEditId >=0 ) {
            // edit mod
            const temp = this.state.questions;
            temp[this.state.currentEditId] = data;
            this.setState({
                questions: temp,
                currentEditId: -1,
            });
        } else {
            // create mod
            const temp = [...this.state.questions, data];
            this.setState({
                questions: temp,
            });
        }
        this.closeEditMod();
    }

    onQuestionDeletion = (index) => {
        const temp = [...this.state.questions];
        temp.splice(index, 1);
        this.setState({
            questions: temp,
        });
    }

    renderQuestions = (questions) => {
        return questions.map((question, index) => {

        const options = question.options.map((option, i) => <div className='option' key={option.name}>option {i+1}:{option.name}</div>);

            return (
                <div key={question.questionName}>
                    <div className='question_title'>
                        Question {index+1}: 
                        <span className='blue'>({question.type==='multiple'? 'Multiple choice' : 'Single choice'})</span> 
                        {question.questionName}
                        <DeleteOutlined onClick={this.onQuestionDeletion.bind(this, index)} />
                        <EditOutlined onClick={this.openEditMod.bind(this, index)} />
                    </div>
                    
                    <div>{options}</div>
                </div>
            );
        });
    }

    onTitleChange = (e) => {
        const { value } = e.target;
        this.setState({
            title: value,
        });
    }

    check = () => {
        return !!this.state.title.trim() && this.state.questions.length > 0
    }

    onCreate = () => {
        const o = {
            title: this.state.title.trim(),
            questions: this.state.questions,
        };
        createPoll(o).then(
            (res) => {
                console.log(res)
            }, 
            (e) => {
                console.log(e);
            });
    }

    render() {

        const questions = this.renderQuestions(this.state.questions);
        
        return (
            <React.Fragment>
                <div className="main">
                    <h1>Start a New Poll</h1>
                    <span>Title:</span>
                    <Input value={this.state.title} onChange={this.onTitleChange} placeholder="Title" />
                    <div className='questions'>{questions}</div>
                    <Button type="primary" onClick={this.openEditMod.bind(this, -1)}><PlusCircleOutlined />Add a new question</Button>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <Button type="primary" 
                        disabled={!this.check()} 
                        onClick={this.onCreate}
                    >
                        <CarryOutOutlined />Create it!
                    </Button>
                </div>
                <Modal
                    title="New Question"
                    visible={this.state.editMod}
                    footer={null}
                    onCancel={this.closeEditMod}
                    maskClosable={false}
                    destroyOnClose={true}
                >
                    <QuestionForm 
                        onCancel={this.closeEditMod} 
                        onConfirm={this.onQuestionConfirm} 
                        questions={this.state.questions}
                        editIndex={this.state.currentEditId}
                    />
                </Modal>
            </React.Fragment>
        );
    }
}

export default Main;
