import React from 'react';
import { Input, Button, Radio } from 'antd';
import {
    DeleteOutlined,
} from '@ant-design/icons';


class QuestionForm extends React.Component {
    constructor(props) {
        super(props);
        if (props.editIndex >= 0) {
            const question = props.questions[props.editIndex];
            this.state = {
                questionName: question.questionName,
                type: question.type,
                options: [...question.options],
            };
        } else {
            this.state = {
                questionName: '',
                type: 'single',
                options:[],
            };
        }
    }

    onNameChange = (e) => {
        const { value } = e.target;
        this.setState({
            questionName: value,
        });
    }

    onAddOption = () => {
        const option = {
            name: '',
        };
        this.setState({
            options: [...this.state.options, option],
        })
    }

    componentDidMount(){}

    onTypeChange = (e) => {
        this.setState({
            type: e.target.value,
          });
    }

    onOptionChange = (index, e) => {
  
        const { value } = e.target;
        const temp = [...this.state.options];
        temp[index].name = value;

        this.setState({
            options: temp,
        });
    }

    onOptionDeletion = (index) => {
        const temp = [...this.state.options];
        temp.splice(index, 1);
        this.setState({
            options: temp,
        });
    }

    onConfirm = () => {
        const o = {
            questionName: this.state.questionName,
            options: this.state.options,
            type: this.state.type,
        };
        this.props.onConfirm(o);
    }

    checkState = () => {
        const hasName = !!this.state.questionName.trim();
        let hasOptions = false;
        let emptyOptions = false;
        for (const option of this.state.options) {
            hasOptions = true;
            if (option.name.trim() === '') emptyOptions = true;
        }
        return hasName && hasOptions && !emptyOptions;
    }

    randerOptions(options) {
        return options.map((option, index) => 
            <div key={index} className='section'>
                <span>Option {index+1}</span>
                <DeleteOutlined onClick={this.onOptionDeletion.bind(this, index)}/>
                <Input onChange={this.onOptionChange.bind(this, index)} value={option.name} placeholder='Optioin' />
            </div>
        );
    }

    render() {

        const options = this.randerOptions(this.state.options);
        const okToSumbit = this.checkState();

        return (
            <div className='question_form'>
                <span>Question Name:</span>
                <Input onChange={this.onNameChange} value={this.state.questionName} placeholder='Question Name' />
                <div className='section'>
                    <Radio.Group onChange={this.onTypeChange} value={this.state.type}>
                        <Radio value={'multiple'}>Multiple Choice</Radio>
                        <Radio value={'single'}>Single Choice</Radio>
                    </Radio.Group>
                </div>
                {options}
                <Button onClick={this.onAddOption} type='primary'>Add a New Option</Button>
                <div className='footer'>
                    <Button onClick={this.props.onCancel}>Cancel</Button>
                    <Button onClick={this.onConfirm} type='primary' disabled={!okToSumbit}>Confirm</Button>
                </div>
                
            </div>
        );
    }
}

export default QuestionForm;