import { useParams } from 'react-router-dom';

export default function SkillPage() {
    let { id } = useParams();

    return <h1>Skill Page for id {id}</h1>;
}
